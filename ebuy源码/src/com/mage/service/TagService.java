package com.mage.service;

import java.util.List;

import com.mage.vo.Tag;

public interface TagService {
	//查询所有标签
	List<Tag> findAll();
}
