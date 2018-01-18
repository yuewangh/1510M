package com.wy.dao.role;

import java.util.List;

import com.wy.model.role.Role;

public interface RoleDao {

	//新增
	public int insert(Role role);

	//修改
	public int update(Role role);

	//删除
	public int delete(String id);

	//根据Id查询单条记录
	public Role getById(String id);

	//分页查询列表
	public List<Role> getList(Role role);
	
	//分页查询数量
	public int getCount(Role role);
	
	//批量删除
	public int deleteAll(String[] id);

}