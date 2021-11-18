package cn.tedu.store.mapper;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;

public interface OrderMapper {
	
	Integer insertOrder(Order order);

	Integer insertOrderItem(OrderItem orderItem);
	
}
