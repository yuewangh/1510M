package com.wy.model.user;

import com.wy.model.BaseModel;
/**
 * 学生
 * @author Administrator
 */
public class User extends BaseModel{

	private String id;//主键
	
	private String user_name;//用户名
	
	private String password;//密码
	
	private String name;//姓名昵称
	
	private String rights;//权限
	
	private String role_id;//角色ID
	
	private String lastlogin;//最后登录时间
	
	private String ip;//ip地址
	
	private String skin;//皮肤
	
	private String email;//邮箱
	
	private String phone;//电话
	
	private String sex;//性别
	

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return id;
	}
	
	public void setUser_name(String user_name){
		this.user_name=user_name;
	}

	public String getUser_name(){
		return user_name;
	}
	
	public void setPassword(String password){
		this.password=password;
	}

	public String getPassword(){
		return password;
	}
	
	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}
	
	public void setRights(String rights){
		this.rights=rights;
	}

	public String getRights(){
		return rights;
	}
	
	public void setRole_id(String role_id){
		this.role_id=role_id;
	}

	public String getRole_id(){
		return role_id;
	}
	
	public void setLastlogin(String lastlogin){
		this.lastlogin=lastlogin;
	}

	public String getLastlogin(){
		return lastlogin;
	}
	
	public void setIp(String ip){
		this.ip=ip;
	}

	public String getIp(){
		return ip;
	}
	
	public void setSkin(String skin){
		this.skin=skin;
	}

	public String getSkin(){
		return skin;
	}
	
	public void setEmail(String email){
		this.email=email;
	}

	public String getEmail(){
		return email;
	}
	
	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return phone;
	}
	
	public void setSex(String sex){
		this.sex=sex;
	}

	public String getSex(){
		return sex;
	}
	
}
