package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressCountLimitException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

public interface IAddressService {
	
	int ADDRESS_MAX_COUNT = 20;
	
	void addnew(Address address, Integer uid, String username) throws AddressCountLimitException, InsertException;
	
	List<Address> getByUid(Integer uid);
	
	void setDefault(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, UpdateException;

	void delete(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, DeleteException, UpdateException;

	Address getByAid(Integer aid);
}
