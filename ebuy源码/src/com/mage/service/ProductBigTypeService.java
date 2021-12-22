package com.mage.service;

import java.util.List;

import com.mage.vo.ProductBigType;

public interface ProductBigTypeService {
	//查询所有大类别的信息
	List<ProductBigType> findAllBigType();
	
}
