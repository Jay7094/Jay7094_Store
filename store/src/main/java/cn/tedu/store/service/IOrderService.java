package cn.tedu.store.service;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.ex.InsertException;

public interface IOrderService {
	
	Order create(Integer aid, Integer[] cids, Integer uid, String username) 
			throws InsertException;
	
}
