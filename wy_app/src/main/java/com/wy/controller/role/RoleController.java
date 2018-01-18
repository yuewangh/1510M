package com.wy.controller.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import com.wy.model.role.Role;
import com.wy.service.role.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController{

	@Autowired
	private RoleService roleService;
	
	
	//查询分页列表
	@RequestMapping("/list")
	public Map<String, Object> list(HttpServletRequest request,Role role){
		Map<String, Object> res = new HashMap<String, Object>();
		int count = roleService.getCount(role);
		List<Role> rolelist = roleService.getList(role);
		res.put("total", count);
		res.put("rows", rolelist);
		return res;
	}
	
	//保存
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,Role role){
		Map<String, Object> map = new HashMap<String, Object>();
		String id= role.getId();
		if(id != null && !id.equals("")){
			roleService.update(role);
		} else{
			role.setId(UUID.randomUUID().toString());
			roleService.insert(role);
		}
		map.put("success", true);
		return map;
	}

	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request,String id){
		Map<String, Object> map = new HashMap<String, Object>();
		roleService.delete(id);
		map.put("success", true);
		return map;
	}

	//进入编辑页面
	@RequestMapping("/getOne")
	@ResponseBody
	public Role edit(HttpServletRequest request,String id){
	 	Role role =null;
		if(id != null && !id.equals("")){
			role = roleService.getById(id);
		}
		return role;
	}

}