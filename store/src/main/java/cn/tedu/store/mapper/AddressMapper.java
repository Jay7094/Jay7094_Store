package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Address;

/**
 * 處理收穫地址數據的持久層接口
 * @author user
 *
 */
public interface AddressMapper {
	
	/**
	 * 插入收穫地址數據
	 * @param address 收穫地址數據
	 * @return 受影響的行數
	 */
	Integer insert(Address address);
	
	Integer deleteByAid(Integer aid);
	
	Integer countByUid(Integer uid);
	
	List<Address> findByUid(Integer uid);
	
	Address findByAid(Integer aid);
	
	Address findLastModified(Integer uid);
	
	Integer updateNonDefault(Integer uid);

	Integer updateDefault(
		@Param("aid") Integer aid, 
		@Param("modifiedUser") String modifiedUser, 
		@Param("modifiedTime") Date modifiedTime);

	
}
