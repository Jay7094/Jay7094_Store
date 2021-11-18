package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Goods;
import cn.tedu.store.mapper.GoodsMapper;
import cn.tedu.store.service.IGoodsService;


@Service
public class GoodsServiceImpl implements IGoodsService{
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public List<Goods> getHotList() {
		// TODO Auto-generated method stub
		return findHotList();
	}
	
	private List<Goods> findHotList(){
		return goodsMapper.findHotList();
	}

	@Override
	public Goods getById(long id) {
		// TODO Auto-generated method stub
		return findById(id);
	}
	
	private Goods findById(long id){
		return goodsMapper.findById(id);
	}
}
