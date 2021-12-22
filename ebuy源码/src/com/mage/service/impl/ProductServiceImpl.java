package com.mage.service.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mage.service.ProductService;
import com.mage.util.DBUtil;
import com.mage.vo.PageBean;
import com.mage.vo.Product;

public class ProductServiceImpl implements ProductService {
	@Override//商品详情
	public Product getProductById(int productId) {
		Product product = null;
		Connection conn = null;
		conn = DBUtil.getConn();
		String sql ="select * from t_product where id = ?";
		//设置绑定变量
		Object [] obj= new Object[] {productId};
		//创建查询对象
		QueryRunner qr = new QueryRunner();
		//进行查询
		try {
			product = qr.query(conn, sql, new BeanHandler<Product>(Product.class),obj);
		} catch (SQLException e) {
			System.out.println("getProductById error");
			e.printStackTrace();
		}finally {
			DBUtil.closeAll(conn, null, null);
		}
		return product;
	}
	@Override
	public int getProductCountBigType(int bigTypeId) {
		//声明一个结果
		int i =0;
		//声明连接
		Connection conn =null;
		try {
			//获取连接
			conn = DBUtil.getConn();
			//写sql
			String sql ="select * from t_product where bigtypeid =  ? ";
			//设置绑定变量
			Object[] obj =new  Object[] {bigTypeId };
			//创建queryrunner
			QueryRunner  qr = new QueryRunner();
			List ls = qr.query(conn, sql, new BeanListHandler<Product>(Product.class),obj);
			//执行 list.size();
			 i = ls.size();
		} catch (SQLException e) {
			System.out.println("getProductCountBigType error ");
			e.printStackTrace();
		}
		return i;
		
		//原始JDBC
//		int i = 0;
//		Connection conn =null;
//		try {
//			conn = DBUtil.getConn();
//			String sql ="select * from t_product where bigtypeid =" + bigTypeId;
//			PreparedStatement psmt = conn.prepareStatement(sql);
//			ResultSet rs =  psmt.executeQuery();
//			if(rs.next()) {
//				 i =rs.getInt(1);
//			}else {
//				return 0;
//			}
//		} catch (SQLException e) {
//			System.out.println("getProductCountBigType error");
//			e.printStackTrace();
//		}finally {
//			DBUtil.closeAll(conn, null, null);
//		}
//		return i;
	}
	
	@Override//通过ID查询分类的list集合商品
	public List<Product> findProductBigType(PageBean pageBean, int bigTypeId) {
		List<Product> productList = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_product where bigtypeid = ? order by hottime limit ? , ?";
			//设置绑定变量
			Object[] obj = new Object[] {
					bigTypeId,	(pageBean.getPage() - 1) * (pageBean.getPagesize()),
					pageBean.getPagesize() 
			};
			//创建queryrunner
			QueryRunner qr = new QueryRunner();
			//执行query
			productList = qr.query(conn, sql, new BeanListHandler<Product>(Product.class) ,obj );
			System.out.println("productList ="+productList);
		} catch (SQLException e) {
			System.out.println("findProductBigType error" );
			e.printStackTrace();
		}
		//关闭连接
		finally {
			DBUtil.closeAll(conn, null, null);
		}
		//返回list
		return productList;
	}
	
	@Override // top搜索按钮查询数据总数量
	public int getProductCount(String searchName) {
		// 声明结果集
		int i = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConn();

			QueryRunner qr = new QueryRunner();

			String sql = "SELECT * FROM T_PRODUCT WHERE NAME LIKE ? ";

			Object[] obj = new Object[] { "%" + searchName + "%" };

			List<Product> ls = qr.query(conn, sql, new BeanListHandler<Product>(Product.class), obj);
			i = ls.size();

		} catch (SQLException e) {
			System.out.println("findproductlist ERROR");
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, null, null);
		}
		return i;
	}

	@Override // top搜索按钮查询list集合
	public List<Product> findProductList(PageBean pageBean, String searchName) {
		List<Product> productList = null;
		Connection conn = null;
		try {
			conn = DBUtil.getConn();
			QueryRunner qr = new QueryRunner();
			String sql = "SELECT * FROM T_PRODUCT WHERE NAME LIKE ? ORDER BY HOTTIME LIMIT ?,?";

			Object[] obj = new Object[] { "%" + searchName + "%", (pageBean.getPage() - 1) * (pageBean.getPagesize()),
					pageBean.getPagesize() };

			productList = qr.query(conn, sql, new BeanListHandler<Product>(Product.class), obj);
		} catch (SQLException e) {
			System.out.println("FINDPRODUCTLIST ERROR");
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, null, null);
		}
		return productList;
	}

	// 左侧小分类查询list集合
	@Override
	public List<Product> findProductListSmallType(PageBean pageBean, int smallType) {
		List<Product> ls = new ArrayList<>();
		// 声明连接
		Connection conn = null;
		try {
			// 获取
			conn = DBUtil.getConn();
			// 把增删改查封装到此对象中用
			QueryRunner qr = new QueryRunner();
			String sql = "select * from t_product where smalltypeId= ? order by hottime limit ?,?";
			// 当前第几条记录开始
			Object[] obj = new Object[] { smallType, (pageBean.getPage() - 1) * (pageBean.getPagesize()),
					// 当前显示的记录数
					pageBean.getPagesize() };
			// Handler处理器对象接口 ；BeanList多条都能查，通过反射将查询出来的结果封装到Poduct对象中
			ls = qr.query(conn, sql, new BeanListHandler<Product>(Product.class), obj);
		} catch (SQLException e) {
			System.out.println("findProductListSmallType error");
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, null, null);
		}
		// 返回获取到的页面记录内容
		return ls;
	}

	// 查询小分类对应的商品总数量
	@Override
	public int getProductCountSmallType(int smallType) {
		List<Product> ls = new ArrayList<>();
		int i = 0;
		// 声明连接
		Connection conn = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_product where smalltypeId = ?";
			Object[] obj = new Object[] { smallType };
			QueryRunner qr = new QueryRunner();
			ls = qr.query(conn, sql, new BeanListHandler<Product>(Product.class), obj);
			i = ls.size();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, null, null);
		}
		// 返回的总记录数
		return i;
	}

	@Override // 热度
	public List<Product> findSpecialPrice() {
		List<Product> productList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from  t_product WHERE specialPrice = 1 and id<41 order by specialpricetime desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setDescription(rs.getString("description"));
				product.setHot(rs.getInt("hot"));
				product.setHotTime(rs.getDate("hotTime"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setProPic(rs.getString("proPic"));
				product.setSpecialPrice(rs.getInt("specialPrice"));
				product.setSpecialPriceTime(rs.getDate("specialPriceTime"));
				product.setStock(rs.getInt("stock"));
				productList.add(product);
			}
			return productList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

	@Override // 特价
	public List<Product> findHotProductList() {
		List<Product> productList = new ArrayList<>();
		// 声明链接
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConn();
			String sql = "select * from t_product where hot =1 and id < 17 order by hottime desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setDescription(rs.getString("description"));
				product.setHot(rs.getInt("hot"));
				product.setHotTime(rs.getDate("hotTime"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getInt("price"));
				product.setProPic(rs.getString("proPic"));
				product.setSpecialPrice(rs.getInt("specialPrice"));
				product.setSpecialPriceTime(rs.getDate("specialPriceTime"));
				product.setStock(rs.getInt("stock"));
				productList.add(product);
			}
			return productList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(conn, pstmt, rs);
		}
		return null;
	}

	

	

	
	
}