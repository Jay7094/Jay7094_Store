package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	//聲明持久層對象
	private UserMapper userMapper;

	@Override
	public void reg(User user) throws UsernameDuplicateException, InsertException {
		//根據參數user對象中的username屬性查詢數據
		String username = user.getUsername();
		User result = userMapper.findByUsername(username);
		//判斷查詢結果是否不為null(查詢結果是存在的)
		if(result != null){
			throw new UsernameDuplicateException("註冊失敗, "+username+"已被占用");
		}
		
		//得到鹽值
		System.err.println("reg() > password= "+user.getPassword());
		String salt = UUID.randomUUID().toString();
		//基於參數user對象中的password進行加密, 得到加密後的密碼
		String md5Password = getMd5Password(user.getPassword(), salt);
		//將加密後的密碼跟鹽值封裝到user中
		user.setSalt(salt);
		user.setPassword(md5Password);
		System.err.println("reg() > salt= "+salt);
		System.err.println("reg() > md5password= "+md5Password);
		
		//將user中的isDelete設置為0
		user.setIsDelete(0);
		
		//封裝user中的4個日誌屬性
		Date now = new Date();
		user.setCreatedUser(username);
		user.setCreatedTime(now);
		user.setModifiedUser(username);
		user.setModifiedTime(now);
		
		//執行註冊
		Integer rows = userMapper.insert(user);
		if(rows != 1){
			throw new InsertException("註冊失敗, 寫入數據錯誤");
		}
	}
	
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		// 根据参数username执行查询用户数据
		User result = userMapper.findByUsername(username);
		
		// 判断查询结果是否为null
		// 抛出：UserNotFoundException
		if(result == null){
			throw new UserNotFoundException("登錄失敗, 用戶名不存在");
		}
		
		// 判断查询结果中的isDelete为1
		// 抛出：UserNotFoundException
		if(result.getIsDelete() == 1){
			throw new UserNotFoundException("登錄失敗, 用戶名不存在");
		}
		
		// 从查询结果中获取盐值
		// 根据参数password和盐值一起进行加密，得到加密后的密码
		// 判断查询结果中的password和以上加密后的密码是否不一致
		// 抛出：PasswordNotMatchException
		String salt = result.getSalt();
		String md5Password = getMd5Password(password, salt);
		if(!result.getPassword().equals(md5Password)){
			throw new PasswordNotMatchException("登錄失敗, 密碼不對");
		}
		
		// 将查询结果中的password、salt、isDelete设置为null
		// 因為是隱藏的數據所以應該只能看到用戶名之類的
		// 返回查询结果
		result.setPassword(null);
		result.setSalt(null);
		result.setIsDelete(null);
		return result;
	}
	
	@Override
	public void changePassword(Integer uid, String username, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateException {
		// TODO Auto-generated method stub
		// 根据参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果是否为null
		// 抛出：UserNotFoundException
		if(result == null){
			throw new UserNotFoundException("修改密碼失敗, 用戶名不存在");
		}
				
		// 判断查询结果中的isDelete为1
		// 抛出：UserNotFoundException
		if(result.getIsDelete() == 1){
			throw new UserNotFoundException("修改密碼失敗, 用戶名不存在");
		}

		// 从查询结果中获取盐值
		// 根据参数oldPassword和盐值一起进行加密，得到加密后的密码
		// 判断查询结果中的password和以上加密后的密码是否不一致
		// 抛出：PasswordNotMatchException
		String salt = result.getSalt();
		String oldMd5Password = getMd5Password(oldPassword, salt);
		
		if(!result.getPassword().equals(oldMd5Password)){
			throw new PasswordNotMatchException(
					"修改密碼失敗!!! 原密碼錯誤");
		}
		
		// 根据参数newPassword和盐值一起进行加密，得到加密后的密码
		// 创建当前时间对象
		// 执行更新密码，并获取返回的受影响的行数
		// 判断受影响的行数是否不为1
		// 抛出：UpdateException
		String newMd5Password = getMd5Password(newPassword, salt);
		Date now = new Date();
		Integer rows =  userMapper.updatePassword(uid, newMd5Password,	username, now);
		if(rows !=1){
			throw new UpdateException("修改密碼失敗~出現未知錯誤");
		}
	}

	public User getByUid(Integer uid){
		//根據UID查詢數據
		User result = userMapper.findByUid(uid);
		//但如果查到數據應該將結果中的password salt is_delete設置為null
		if(result != null){
			result.setPassword(null);
			result.setSalt(null);
			result.setIsDelete(null);
		}
		//將結果返回
		return result;
	}
	
	public void changeInfo(User user) throws UserNotFoundException,UpdateException{
		// 根据参数user中的uid，即user.getUid()查询数据
		// 检查查询结果是否存在，是否标记为删除
		User result = userMapper.findByUid(user.getUid());
		// 判断查询结果是否为null
		// 抛出：UserNotFoundException
		if(result == null){
			throw new UserNotFoundException("修改個人資料失敗, 用戶名不存在");
		}			
		// 判断查询结果中的isDelete为1
		// 抛出：UserNotFoundException
		if(result.getIsDelete() == 1){
			throw new UserNotFoundException("修改個人資料失敗, 用戶名不存在");
		}
		
		// 创建当前时间对象 因為modified time需要時間
		Date now = new Date();
		// 将时间封装到参数user中
		user.setModifiedUser(user.getUsername());
		user.setModifiedTime(now);
		// 执行修改个人资料：mapper.updateInfo(user) > update t_user set phone=?, email=?, gender=?, modified_user=?, modified_time=? where uid=?
		Integer rows = userMapper.updateInfo(user);
		// 判断以上修改时的返回值是否不为1
		if(rows != 1){
			// 抛出：UpdateException 
			throw new UpdateException("修改個人資料失敗");
		}
		
	}
	
	public void changeAvatar(
			Integer uid, String username, String avatar) 
				throws UserNotFoundException, UpdateException {
		// 根据参数uid查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果是否为null
		// 抛出：UserNotFoundException
		if(result == null){
			throw new UserNotFoundException("修改頭像失敗, 用戶名不存在");
		}
						
		// 判断查询结果中的isDelete为1
		// 抛出：UserNotFoundException
		if(result.getIsDelete() == 1){
			throw new UserNotFoundException("修改頭像失敗, 用戶名不存在");
		}
		// 創建當前時間對象
		Date now = new Date();
		//執行更新頭像
		Integer rows = userMapper.updateAvatar(uid, avatar, username, now);
		// 判断受影响的行数是否不为1
		if(rows !=1){
			throw new UpdateException("修改頭像失敗~出現未知錯誤");
		}
	}
	
	/**
	 * 對密碼進行加密
	 * @param password 原始密碼
	 * @param salt 鹽值
	 * @return 加密後的密碼
	 */
	private String getMd5Password(String password, String salt){
		//規則: 對password+salt進行三重加密
		String str = password + salt;
		for(int i=0; i<3; i++){
			str = DigestUtils.md5DigestAsHex(str.getBytes());
		}
		return str;
	}
	
}












