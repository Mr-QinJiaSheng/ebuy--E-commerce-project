package com.mage.service;

import java.util.List;

import com.mage.vo.OrderProduct;

public interface OrderProductService {
	//保存订单商品
	void save(OrderProduct op);
	//通话id来查询所有订单
	List<OrderProduct> findByOrderId(int Id);
}
