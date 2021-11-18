package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Goods;

public interface GoodsMapper {
	
	List<Goods> findHotList();
	
	Goods findById(long id);
}
