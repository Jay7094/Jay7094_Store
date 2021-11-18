package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.vo.CartVO;

@Service
public class OrderServiceImpl implements IOrderService{
	
	@Autowired private OrderMapper orderMapper;
	@Autowired private IAddressService addressService;
	@Autowired private ICartService cartService;
	
	private void insertOrder(Order order) throws InsertException {
		Integer rows = orderMapper.insertOrder(order);
		if (rows != 1) {
			throw new InsertException(
				"创建订单失败！插入订单数据时出现未知错误！");
		}
	}
	
	private void insertOrderItem(OrderItem orderItem) throws InsertException {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if (rows != 1) {
			throw new InsertException(
				"创建订单失败！插入订单商品数据时出现未知错误！");
		}
	}
	
	@Transactional
	public Order create(Integer aid, Integer[] cids, Integer uid, String username) throws InsertException {
		
		Date now = new Date();

		// 根据参数cids查询对应的购物车数据，得到List<CartVO>对象
		List<CartVO> carts = cartService.getByCids(cids, uid);
		// 遍历以上查询到的对象，根据各元素的price和num计算得到总价
		Long totalPrice = 0L;
		for (CartVO cartVO : carts) {
			totalPrice += cartVO.getPrice() * cartVO.getNum();
		}

		// 根据收货地址aid查询收货地址详情
		Address address = addressService.getByAid(aid);

		// 创建order对象：Order order = new Order();
		Order order = new Order();
		// 封装order对象中的属性值：order.setUid(uid);
		order.setUid(uid);
		// 封装order对象中的属性值：recv_name,recv_phone,recv_address
		order.setRecvName(address.getName());
		order.setRecvPhone(address.getPhone());
		order.setRecvAddress(address.getProvinceName() + address.getCityName() + address.getAreaName() + address.getAddress());
		// 封装order对象中的属性值：total_price
		order.setTotalPrice(totalPrice);
		// 封装order对象中的属性值：state(0)
		order.setState(0);
		// 封装order对象中的属性值：order_time(now)
		order.setOrderTime(now);
		// 封装order对象中的属性值：pay_time(null)
		order.setPayTime(null);
		// 封装order对象中的属性值：日志
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifiedUser(username);
		order.setModifiedTime(now);
		// 插入订单数据：insertOrder(order)
		insertOrder(order);

		// 遍历以上查询得到的List<CartVO>对象
		for (CartVO cartVO : carts) {
			// 创建orderItem对象：OrderItem ordeItemr = new OrderItem();
			OrderItem orderItem = new OrderItem();
			// 封装orderItem对象中的属性值：oid
			orderItem.setOid(order.getOid());
			// 封装orderItem对象中的属性值：gid,price,title,image,num
			orderItem.setGid(cartVO.getGid());
			orderItem.setPrice(cartVO.getPrice());
			orderItem.setTitle(cartVO.getTitle());
			orderItem.setImage(cartVO.getImage());
			orderItem.setNum(cartVO.getNum());
			// 封装orderItem对象中的属性值：日志
			orderItem.setCreatedUser(username);
			orderItem.setCreatedTime(now);
			orderItem.setModifiedUser(username);
			orderItem.setModifiedTime(now);
			// 插入订单商品数据：insertOrderItem(itemItem)
			insertOrderItem(orderItem);
		}
		
		// TODO 删除购物车中对应的数据
		// TODO 修改对应的商品的库存量

		// 返回
		return order;
	}

}
