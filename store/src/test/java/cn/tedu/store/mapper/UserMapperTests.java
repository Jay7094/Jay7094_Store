package cn.tedu.store.mapper;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {
	
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void insert(){
		User user = new User();
		user.setUsername("root");
		user.setPassword("1234");
		Integer rows = mapper.insert(user);
		System.err.println("row= "+rows);
	}
	
	@Test
	public void findByUsername(){
		String username = "root";
		User user = mapper.findByUsername(username);
		System.err.println(user);
	}
	
	@Test
	public void updatePassword(){
		Integer uid = 6;
		String password = "88888";
		String modifiedUser = "超級管理員";
		Date modifiedTime = new Date();
		Integer rows = mapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		System.err.println("row= "+rows);
	}
	
	@Test
	public void updateInfo(){
		User user = new User();
		user.setUid(12);
		user.setPhone("1233211234567");
		user.setEmail("haha@EEE");
		user.setGender(1);
		Integer rows = mapper.updateInfo(user);
		System.err.println("row= "+rows);
	}
	
	@Test
	public void updateAvatar(){
		Integer uid = 12;
		String avatar = "頭像路徑";
		String modifiedUser = "超級管理員";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		System.err.println("row= "+rows);
	}
}






