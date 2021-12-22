package com.mage.service;

import java.util.List;

import com.mage.vo.News;

public interface NewsService {
	//查询所有新闻信息
	List<News> findAll();
	//通过ID来获取新闻内容
	News getNewsById(int newsId);
}
