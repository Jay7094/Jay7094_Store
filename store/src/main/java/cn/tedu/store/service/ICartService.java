package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

public interface ICartService {
	
	void addToCart(Cart cart, Integer uid, String username) 
			throws InsertException, UpdateException;
	
	List<CartVO> getByUid(Integer uid);
	
	Integer add(Integer cid, Integer uid, String username) 
			throws CartNotFoundException, AccessDeniedException, UpdateException;
	
	List<CartVO> getByCids(Integer[] cids, Integer uid);

}
