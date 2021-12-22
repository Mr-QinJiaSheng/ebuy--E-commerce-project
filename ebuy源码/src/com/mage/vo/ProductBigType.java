package com.mage.vo;

import java.util.ArrayList;
import java.util.List;

//大分类
public class ProductBigType {
	private Integer id;//id
	private String name;//名称
	private String remarks;//备注
	private List<ProductSmallType> smallTypeList = new ArrayList<ProductSmallType>();//小类别集合
	public ProductBigType() {
		// TODO Auto-generated constructor stub
	}

	public ProductBigType(Integer id, String name, String remarks, List<ProductSmallType> smallTypeList) {
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.smallTypeList = smallTypeList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<ProductSmallType> getSmallTypeList() {
		return smallTypeList;
	}

	public void setSmallTypeList(List<ProductSmallType> smallTypeList) {
		this.smallTypeList = smallTypeList;
	}

	@Override
	public String toString() {
		return "ProductBigType [id=" + id + ", name=" + name + ", remarks=" + remarks + "]";
	}
}	
