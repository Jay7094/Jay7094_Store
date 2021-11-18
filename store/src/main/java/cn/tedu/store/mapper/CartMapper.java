package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

public interface CartMapper {
	
	Integer insert(Cart cart);

	Integer updateNum(
		@Param("cid") Integer cid, 
		@Param("num") Integer num, 
		@Param("modifiedUser") String modifiedUser, 
		@Param("modifiedTime") Date modifiedTime
	);

	Cart findByUidAndGid(
		@Param("uid") Integer uid,
		@Param("gid") Long gid
	);
	
	List<CartVO> findByUid(Integer uid);
	
	Cart findByCid(Integer cid);
	
	List<CartVO> findByCids(Integer[] cids);
}
