package com.wy.service.user.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wy.model.user.User;
import com.wy.dao.user.UserDao;
import com.wy.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(User user) {
		return userDao.insert(user);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(User user) {
		return userDao.update(user);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int delete(String id) {
		return userDao.delete(id);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public User getById(String id) {
		return userDao.getById(id);
	}
	
	/**
	 * 查询分页列表
	 */
	@Override
	public List<User> getList(User user) {
		return userDao.getList(user);
	}
	
	//分页查询数量
	public int getCount(User user){
		return userDao.getCount(user);
	}
	
	/**
	 * 删除所有
	 */
	@Override
	public int deleteAll(String[] id) {
		return userDao.deleteAll(id);
	}

}