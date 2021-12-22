package com.mage.service;

import java.util.List;

import com.mage.vo.PageBean;
import com.mage.vo.Product;

public interface ProductService {
	// 左侧的小分类查询list集合
	List<Product> findProductListSmallType(PageBean pageBean, int smallType);

	// 左侧的小分类查询分页的总记录数
	int getProductCountSmallType(int smallType);

	// 首页特价商品列表展示
	List<Product> findSpecialPrice();

	// 首页热度商品的列表展示
	List<Product> findHotProductList();

	// 首页上搜索按钮
	List findProductList(PageBean pageBean, String searchName);

	// 首页搜索安装查询总数量
	int getProductCount(String productName);
	//通过ID查询分类的list集合商品
	List<Product> findProductBigType(PageBean pageBean, int bigTypeId);
	//通过ID查询大分类分页总数
	int getProductCountBigType(int bigTypeId);
	//商品的详情展示
	Product getProductById(int productId);

}
