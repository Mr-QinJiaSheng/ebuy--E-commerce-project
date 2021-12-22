package com.mage.service;

import java.util.List;

import com.mage.vo.Notice;

public interface NoticeService {
	//公告的接口
	List<Notice> findAll();
	//通过公告ID获取内容
	Notice getNoticeById(int noticeId);
}
