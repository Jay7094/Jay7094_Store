package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {
	
	@Autowired
	private AddressMapper mapper;
	
	@Test
	public void insert(){
		Address address = new Address();
		address.setUid(1);
		address.setName("小林");
		Integer rows = mapper.insert(address);
		System.err.println(rows);
		System.err.println(address.getAid());
	}
	
	@Test
	public void countByUid(){
		Integer uid = 1;
		Integer count = mapper.countByUid(uid);
		System.err.println(count);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 12;
		List<Address> list= mapper.findByUid(uid);
		System.err.println("BEGIN:");
		for (Address item : list) {
			System.err.println(item);
		}
		System.err.println("END.");
	}
	
	@Test
	public void updateNonDefault() {
		Integer uid = 12;
		Integer rows = mapper.updateNonDefault(uid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateDefault() {
		Integer aid = 22;
		String modifiedUser = "超级管理员";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateDefault(aid, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}

	@Test
	public void findByAid() {
		Integer aid = 20;
		Address result = mapper.findByAid(aid);
		System.err.println(result);
	}
}









