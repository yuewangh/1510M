package com.wy.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import com.wy.model.user.User;
import com.wy.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController{

	@Autowired
	private UserService userService;
	
	
	//查询分页列表
	@RequestMapping("/list")
	public Map<String, Object> list(HttpServletRequest request,User user){
		Map<String, Object> res = new HashMap<String, Object>();
		int count = userService.getCount(user);
		List<User> userlist = userService.getList(user);
		res.put("total", count);
		res.put("rows", userlist);
		return res;
	}
	
	//保存
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,User user){
		Map<String, Object> map = new HashMap<String, Object>();
		String id= user.getId();
		if(id != null && !id.equals("")){
			userService.update(user);
		} else{
			user.setId(UUID.randomUUID().toString());
			userService.insert(user);
		}
		map.put("success", true);
		return map;
	}

	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request,String id){
		Map<String, Object> map = new HashMap<String, Object>();
		userService.delete(id);
		map.put("success", true);
		return map;
	}

	//进入编辑页面
	@RequestMapping("/getOne")
	@ResponseBody
	public User edit(HttpServletRequest request,String id){
	 	User user =null;
		if(id != null && !id.equals("")){
			user = userService.getById(id);
		}
		return user;
	}

}