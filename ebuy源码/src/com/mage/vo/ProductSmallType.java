package com.mage.vo;
//小分类的
public class ProductSmallType {
	private Integer id;//id
	private String name;//名称
	private String remarks;//备注
	private int bigTypeId;//外键 
	public ProductSmallType() {
		// TODO Auto-generated constructor stub
	}
	
	public ProductSmallType(Integer id, String name, String remarks, int bigTypeId) {
		this.id = id;
		this.name = name;
		this.remarks = remarks;
		this.bigTypeId = bigTypeId;
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

	public int getBigTypeId() {
		return bigTypeId;
	}

	public void setBigTypeId(int bigTypeId) {
		this.bigTypeId = bigTypeId;
	}
}
