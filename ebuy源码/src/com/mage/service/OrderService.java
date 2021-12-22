package com.mage.service;

import java.util.List;

import com.mage.vo.Order;

public interface OrderService {
	//结算
	void saveOrder(Order order);
	//展示订单列表
	
	List<Order> findAll(int userId);
}
