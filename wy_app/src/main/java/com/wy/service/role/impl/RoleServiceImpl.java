package com.wy.service.role.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wy.model.role.Role;
import com.wy.dao.role.RoleDao;
import com.wy.service.role.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(Role role) {
		return roleDao.insert(role);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(Role role) {
		return roleDao.update(role);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int delete(String id) {
		return roleDao.delete(id);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public Role getById(String id) {
		return roleDao.getById(id);
	}
	
	/**
	 * 查询分页列表
	 */
	@Override
	public List<Role> getList(Role role) {
		return roleDao.getList(role);
	}
	
	//分页查询数量
	public int getCount(Role role){
		return roleDao.getCount(role);
	}
	
	/**
	 * 删除所有
	 */
	@Override
	public int deleteAll(String[] id) {
		return roleDao.deleteAll(id);
	}

}