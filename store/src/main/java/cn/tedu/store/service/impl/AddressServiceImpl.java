package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressCountLimitException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

@Service
public class AddressServiceImpl implements IAddressService{
	
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private IDistrictService districtService;
	
	public void addnew(Address address, Integer uid, String username) throws AddressCountLimitException, InsertException {
				// 根据参数uid查询当前用户的收货地址数量
				Integer count = countByUid(uid);
				// 判断收货地址数量是否达到上限值ADDRESS_MAX_COUNT
				if (count >= ADDRESS_MAX_COUNT) {
					// 是：抛出：AddressCountLimitException
					throw new AddressCountLimitException(
						"增加收货地址失败！当前收货地址数量已经达到上限！最多允许创建" + ADDRESS_MAX_COUNT + "条，已经创建" + count + "条！");
				}

				// 补全数据：uid
				address.setUid(uid);

				// TODO 补全数据：province_name, city_name, area_name
				District province = districtService.getByCode(address.getProvinceCode());
				District city = districtService.getByCode(address.getCityCode());
				District area = districtService.getByCode(address.getAreaCode());
				address.setProvinceName(province == null ? "NULL": province.getName());
				address.setCityName(city == null ? "NULL": city.getName());
				address.setAreaName(area == null ? "NULL": area.getName());
				
				// 判断当前用户的收货地址数量是否为0，并决定is_default的值
				Integer isDefault = count == 0 ? 1 : 0;
				// 补全数据：is_default
				address.setIsDefault(isDefault);

				// 创建当前时间对象
				Date now = new Date();
				// 补全数据：4个日志
				address.setCreatedUser(username);
				address.setCreatedTime(now);
				address.setModifiedUser(username);
				address.setModifiedTime(now);

				// 插入收货地址数据
				insert(address);
	}
	
	private void insert(Address address){
		Integer rows = addressMapper.insert(address);
		if (rows != 1) {
			throw new InsertException("增加收貨地址失敗！插入數據時出現未知錯誤！");
		}
	}

	private Integer countByUid(Integer uid){
		return addressMapper.countByUid(uid);
	}

	private List<Address> findByUid(Integer uid){
		return addressMapper.findByUid(uid);
	}

	@Override
	public List<Address> getByUid(Integer uid) {
		return findByUid(uid);
	}
	
	private void updateNonDefault(Integer uid) throws UpdateException {
		Integer rows = addressMapper.updateNonDefault(uid);
		if (rows == 0) {
			throw new UpdateException("設置默認地址失敗");
		}
	}

	private void updateDefault(Integer aid, String modifiedUser, Date modifiedTime) throws UpdateException {
		Integer rows = addressMapper.updateDefault(aid, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("不允許訪問他人數據");
		}
	}

	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}
	
	@Override
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username)
			throws AddressNotFoundException, AccessDeniedException, UpdateException {
		Address result = findByAid(aid);
		if(result == null){
			throw new AddressNotFoundException();
		}
		if(result.getUid() != uid){
			throw new AccessDeniedException();
		}
		updateNonDefault(uid);
		updateDefault(aid, username, new Date());
	}
	
	private void deleteByAid(Integer aid){
		Integer rows = addressMapper.deleteByAid(aid);
		if(rows != 1){
			throw new DeleteException("刪除收穫地址失敗有未知錯誤");
		}
	};
	
	private Address findLastModified(Integer uid){
		return addressMapper.findLastModified(uid);
	}

	@Override
	@Transactional
	public void delete(Integer aid, Integer uid, String username)
			throws AddressNotFoundException, AccessDeniedException, DeleteException, UpdateException {
		Address result = findByAid(aid);
		if(result == null){
			throw new AddressNotFoundException("刪除收貨地址失敗");
		}
		if(result.getUid() != uid){
			throw new AccessDeniedException();
		}
		deleteByAid(aid);
		if(result.getIsDefault() == 0){
			return;
		}
		Integer count = countByUid(uid);
		if(count == 0){
			return;
		}
		Address lastModifiedAddress = findLastModified(uid);
		updateDefault(lastModifiedAddress.getAid(), username, new Date());
	}

	@Override
	public Address getByAid(Integer aid) {
		return findByAid(aid);
	};
}

















