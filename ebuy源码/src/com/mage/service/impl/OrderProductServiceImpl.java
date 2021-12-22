package com.mage.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mage.service.OrderProductService;
import com.mage.service.ProductService;
import com.mage.util.DBUtil;
import com.mage.vo.OrderProduct;

public class OrderProductServiceImpl implements OrderProductService {
	// 声明商品的service
	private ProductService productService = new ProductServiceImpl();

	@Override
	public List<OrderProduct> findByOrderId(int orderId) {
		List<OrderProduct> orderProductList = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_order_product where orderId = ?";
			Object[] obj = new Object[] { orderId };
			QueryRunner qr = new QueryRunner();
			orderProductList = qr.query(conn, sql, new BeanListHandler<OrderProduct>(OrderProduct.class), obj);
			for (OrderProduct op : orderProductList) {
				op.setProduct(productService.getProductById(op.getProductId()));
			}
			
		} catch (SQLException e) {
			System.out.println("OrderProductServiceImpl--findByOrderId--error");
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, null, null);
		}
		return orderProductList;
	}

	@Override
	public void save(OrderProduct op) {
		Connection conn = null;
		conn = DBUtil.getConn();
		String sql = "insert into t_order_product (num,orderId,productId) values(?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] obj = new Object[] { op.getNum(), op.getOrder().getId(), op.getProduct().getId() };
		try {
			qr.update(conn, sql, obj);
		} catch (SQLException e) {
			System.out.println("OrderProductServiceImpl save ");
			e.printStackTrace();
		}
	}

}
