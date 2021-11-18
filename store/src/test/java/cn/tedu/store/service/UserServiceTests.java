package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
	
	@Autowired
	IUserService service;
	
	@Test
	public void reg(){
		try{
			User user = new User();
			user.setUsername("root");
			user.setPassword("1234");
			service.reg(user);
			System.err.println("OK!!!");
		}catch(ServiceException e){
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void login(){
		try{
			String username = "root";
			String password = "1234";
			User user = service.login(username, password);
			System.err.println(user);
		}catch(ServiceException e){
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changePassword(){
		try{
			Integer uid = 10;
			String username = "系統管理員";
			String oldpassword = "1234";
			String newpassword = "12345";
			service.changePassword(uid, username, oldpassword, newpassword);
			System.err.println("OK~");
		}catch(ServiceException e){
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid(){
		Integer uid = 12;
		User user = service.getByUid(uid);
		System.err.println(user);
	}
	
	@Test
	public void changeInfo() {
		try {
			User user = new User();
			user.setUid(12);
			user.setUsername("超級管理員");
			user.setPhone("1233211234567");
			user.setEmail("root@EWRWEFSDF");
			user.setGender(2);
			service.changeInfo(user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeAvatar(){
		try{
			Integer uid = 12;
			String username = "系統管理員";
			String avatar = "新頭像路徑~";
			service.changeAvatar(uid, username, avatar);
			System.err.println("OK~");
		}catch(ServiceException e){
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
}












