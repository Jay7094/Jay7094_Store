package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.IDistrictService;

@Service
public class DistrictServiceImpl implements IDistrictService{
	
	@Autowired
	private DistrictMapper districtMapper;
	
	@Override
	public List<District> getByParent(String parent) {
		// TODO Auto-generated method stub
		return findByParent(parent);
	}
	
	private List<District> findByParent(String parent) {
		return districtMapper.findByParent(parent);
	}
	
	private District findByCode(String code){
		return districtMapper.findByCode(code);
	}

	@Override
	public District getByCode(String code) {
		return findByCode(code);
	};
	
}








