package com.mage.vo;

import java.util.ArrayList;
import java.util.List;

//购物车实体类
public class ShoppingCart {
	private int userId;
	//购物车的商品     			    shoppingCartItems
	private  List<ShoppingCartItem> shoppingCartItems = new ArrayList<ShoppingCartItem>();
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<ShoppingCartItem> getShoppingCartItems() {
		return shoppingCartItems;
	}
	public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
		this.shoppingCartItems = shoppingCartItems;
	}
	
}
