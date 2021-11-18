package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;

/**
 * 處理用戶數據的業務層接口
 * @author user
 *
 */
public interface IUserService {
	
	/**
	 * 用戶註冊
	 * @param user 用戶數據對象
	 * @throws UsernameDuplicateException
	 * @throws InsertException
	 */
	void reg(User user) throws UsernameDuplicateException;
	
	/**
	 * 用戶登錄
	 * @param username 用戶名
	 * @param password 密碼
	 * @return 登錄成功的用戶的信息
	 * @throws UserNotFoundException
	 * @throws PasswordNotMatchException
	 */
	User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException;
	
	/**
	 * 修改密碼
	 * @param uid
	 * @param username
	 * @param oldPassword
	 * @param newPassword
	 * @throws UserNotFoundException
	 * @throws PasswordNotMatchException
	 * @throws UpdateException
	 */
	void changePassword(Integer uid, String username, 
			String oldPassword, String newPassword) 
					throws UserNotFoundException, 
						PasswordNotMatchException, UpdateException;
	
	/**
	 * 根據用戶ID查詢用戶數據
	 * @param uid 用戶的ID
	 * @return 匹配的用戶數據
	 */
	User getByUid(Integer uid);
	
	/**
	 * 更新用戶基本資料
	 * @param user 封裝了用戶基本資料的對象, 封裝用戶的ID和用戶名
	 * @throws UserNotFoundException
	 * @throws UpdateException
	 */
	void changeInfo(User user) throws UserNotFoundException,
		UpdateException;
	
	void changeAvatar(Integer uid, String username, String avatar) 
			throws UserNotFoundException, UpdateException;
}









