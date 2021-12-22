package com.mage.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mage.service.OrderProductService;
import com.mage.service.OrderService;
import com.mage.util.DBUtil;
import com.mage.vo.Order;
import com.mage.vo.OrderProduct;

public class OrderServiceImpl implements OrderService {

	private OrderProductService orderProductService = new OrderProductServiceImpl();

	@Override
	public List<Order> findAll(int userId) {
		List<Order> orderList = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_order where userId = ? ";
			Object[] obj = new Object[] { userId };
			QueryRunner qr = new QueryRunner();
			orderList = qr.query(conn, sql, new BeanListHandler<Order>(Order.class), obj);
			for (Order o : orderList) {
				List<OrderProduct> orderProductList  = orderProductService.findByOrderId(o.getId());
				o.setOrderProductList(orderProductList);
			}
		} catch (SQLException e) {
			System.out.println("OrderServiceImpl -- findAll--error");
			e.printStackTrace();
		}

		return orderList;
	}

	@Override // 插入一条订单
	public void saveOrder(Order order) {
		Connection conn = null;
		conn = DBUtil.getConn();
		String sql = "insert into t_order (cost,createTime,orderNo,status,userId) values(?,?,?,?,?)";
		Object[] obj = new Object[] { order.getCost(), order.getCreateTime(), order.getOrderNo(), order.getStatus(),
				order.getUser().getId()

		};
		QueryRunner qr = new QueryRunner();
		// 此处用beanhadlder返回的是一行(row) ,而scalarHandler 可以处理单数据!
		try {
			Long id = qr.insert(conn, sql, new ScalarHandler<Long>(), obj);
			order.setId(id.intValue());// 类型转换
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, null, null);
		}

	}

}
