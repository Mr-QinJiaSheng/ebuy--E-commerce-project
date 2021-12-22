package com.mage.service;

import com.mage.vo.User;

public interface UserService {
	void register(User user);//用户注册
	boolean checkName(String userName);//检查用户名是否唯一
	//用户登录
	User login(User user);
	//更改用户信息的方法
	void update(User user);
}
