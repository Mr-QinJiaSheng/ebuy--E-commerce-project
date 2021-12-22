package com.mage.service;

import java.util.List;

import com.mage.vo.ProductSmallType;

public interface ProductSmallTypeService {
	//查询大类别的ID
	List<ProductSmallType> findByBigTypeId(int bigTypeId);
    
}
