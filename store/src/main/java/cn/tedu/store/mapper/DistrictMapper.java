package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.District;

public interface DistrictMapper {
	
	List<District> findByParent(String parent);
	
	/**
	 * 根據省市區的代號查詢
	 * @param code
	 * @return 返回名字
	 */
	District findByCode(String code);
}
