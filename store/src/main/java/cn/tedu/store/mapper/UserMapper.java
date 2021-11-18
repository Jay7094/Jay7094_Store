package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

/*
 * 處理用戶數據的持久層接口
 */

public interface UserMapper {
	
	/**
	 * 插入用戶數據
	 * @param user 用戶數據對象
	 * @return 受影響的行數
	 */
	Integer insert(User user);
	
	/**
	 * 根據用戶名查詢用戶數據
	 * @param username 用戶名
	 * @return 匹配的用戶數據, 如果沒有匹配的數據 則返回null
	 */
	User findByUsername(String username);
	
	/**
	 * 根據用戶的ID查詢用戶數據
	 * @param uid 用戶ID
	 * @return 匹配的用戶數據, 如果沒有則返回null
	 */
	User findByUid(Integer uid);
	
	/**
	 * 更新用戶資料
	 * @param user 用戶資料
	 * @return 受影響的行數
	 */
	Integer updateInfo(User user);
	
	/**
	 * 更新密碼
	 * @param uid 用戶的ID
	 * @param password 新密碼
	 * @param modifiedUser 修改執行人
	 * @param modifiedTime 修改時間
	 * @return 受影響的行數
	 */
	Integer updatePassword(
			@Param("uid") Integer uid,
			@Param("password") String password,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime
	);
	
	Integer updateAvatar(
			@Param("uid") Integer uid, 
			@Param("avatar") String avatar,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);

}




