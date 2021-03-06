package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {
	
	@Autowired
	ICartService service;

	@Test
	public void addToCart() {
		try {
			Cart cart = new Cart();
			cart.setGid(10L);
			cart.setNum(5);
			Integer uid = 7;
			String username = "購物人";
			service.addToCart(cart, uid, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid = 12;
		List<CartVO> list = service.getByUid(uid);
		System.err.println("BEGIN:");
		for (CartVO item : list) {
			System.err.println(item);
		}
		System.err.println("END.");
	}
	
	@Test
	public void add() {
		try {
			Integer cid = 3;
			Integer uid = 12;
			String username = "系统管理员";
			Integer newNum = service.add(cid, uid, username);
			System.err.println("OK. new num=" + newNum);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByCids() {
		Integer[] cids = {2,3,4,5};
		Integer uid = 12;
		List<CartVO> list = service.getByCids(cids, uid);
		System.err.println("BEGIN:");
		for (CartVO item : list) {
			System.err.println(item);
		}
		System.err.println("END.");
	}
}
