package com.mage.vo;
//购物车中的商品
public class ShoppingCartItem {
	private int id;
	private Product product; //商品
	private int count;//商品的数量
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
