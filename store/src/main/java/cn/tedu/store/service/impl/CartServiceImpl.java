package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

@Service
public class CartServiceImpl implements ICartService{

	@Autowired 
	private CartMapper cartMapper;
	
	private void insert(Cart cart) throws InsertException {
		Integer rows = cartMapper.insert(cart);
		if (rows != 1) {
			throw new InsertException(
				"将商品添加到购物车失败！插入数据时出现未知错误！");
		}
	}
	
	private void updateNum(Integer cid, Integer num, 
		    String modifiedUser, Date modifiedTime)
				throws UpdateException {
			Integer rows = cartMapper.updateNum(cid, num, modifiedUser, modifiedTime);
			if (rows != 1) {
				throw new UpdateException(
					"更新商品数量失败！更新数据时出现未知错误！");
			}
		}
	
	private Cart findByUidAndGid(Integer uid, Long gid) {
		return cartMapper.findByUidAndGid(uid, gid);
	}
	
	@Override
	public void addToCart(Cart cart, Integer uid, String username) throws InsertException, UpdateException {
		Date now = new Date();
		Cart result = findByUidAndGid(uid, cart.getGid());
		if(result == null){
			cart.setUid(uid);
			cart.setCreatedUser(username);
			cart.setModifiedUser(username);
			cart.setCreatedTime(now);
			cart.setModifiedTime(now);
			insert(cart);
		}else{
			Integer cid = result.getCid();
			Integer oldNum = result.getNum();
			Integer newNum = oldNum + cart.getNum();
			updateNum(cid, newNum, username, now);
		}
	}
	
	private List<CartVO> findByUid(Integer uid) {
		return cartMapper.findByUid(uid);
	}
	
	@Override
	public List<CartVO> getByUid(Integer uid) {
		return findByUid(uid);
	}
	
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	}
	
	@Override
	public Integer add(Integer cid, Integer uid, String username)
			throws CartNotFoundException, AccessDeniedException, UpdateException {
		Cart result = findByCid(cid);
		if(result == null){
			throw new CartNotFoundException("增加數量失敗,嘗試訪問的數據不存在");
		}
		if(result.getUid() != uid){
			throw new AccessDeniedException("增加數量失敗,非法訪問");
		}
		Integer newNum = result.getNum()+1;
		updateNum(cid, newNum, username, new Date());
		return newNum;
	}

	private List<CartVO> findByCids(Integer[] cids) {
		return cartMapper.findByCids(cids);
	}
	
	@Override
	public List<CartVO> getByCids(Integer[] cids, Integer uid) {
		List<CartVO> results = findByCids(cids);
		Iterator<CartVO> it = results.iterator();
		while (it.hasNext()) {
			if (uid != it.next().getUid()) {
				it.remove();
			}
		}
		return results;
	}

	
}
