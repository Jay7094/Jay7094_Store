package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Goods;

public interface IGoodsService {
	
	List<Goods> getHotList();
	
	Goods getById(long id);
}
