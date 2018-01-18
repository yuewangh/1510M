package com.wy.model.role;

import com.wy.model.BaseModel;
/**
 * 学生
 * @author Administrator
 */
public class Role extends BaseModel{

	private String id;//主键
	
	private String role_name;//角色名称
	
	private String rights;//权限
	
	private String add_qx;//新增权限0没有1有
	
	private String del_qx;//删除权限0没有1有
	
	private String edit_qx;//编辑权限0没有1有
	
	private String cha_qx;//查询权限0没有1有
	
	private String sh_qx;//审核权限0没有1有
	
	private String by1_qx;//备用1权限
	
	private String by2_qx;//备用2权限
	
	private String by3_qx;//备用3权限
	

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return id;
	}
	
	public void setRole_name(String role_name){
		this.role_name=role_name;
	}

	public String getRole_name(){
		return role_name;
	}
	
	public void setRights(String rights){
		this.rights=rights;
	}

	public String getRights(){
		return rights;
	}
	
	public void setAdd_qx(String add_qx){
		this.add_qx=add_qx;
	}

	public String getAdd_qx(){
		return add_qx;
	}
	
	public void setDel_qx(String del_qx){
		this.del_qx=del_qx;
	}

	public String getDel_qx(){
		return del_qx;
	}
	
	public void setEdit_qx(String edit_qx){
		this.edit_qx=edit_qx;
	}

	public String getEdit_qx(){
		return edit_qx;
	}
	
	public void setCha_qx(String cha_qx){
		this.cha_qx=cha_qx;
	}

	public String getCha_qx(){
		return cha_qx;
	}
	
	public void setSh_qx(String sh_qx){
		this.sh_qx=sh_qx;
	}

	public String getSh_qx(){
		return sh_qx;
	}
	
	public void setBy1_qx(String by1_qx){
		this.by1_qx=by1_qx;
	}

	public String getBy1_qx(){
		return by1_qx;
	}
	
	public void setBy2_qx(String by2_qx){
		this.by2_qx=by2_qx;
	}

	public String getBy2_qx(){
		return by2_qx;
	}
	
	public void setBy3_qx(String by3_qx){
		this.by3_qx=by3_qx;
	}

	public String getBy3_qx(){
		return by3_qx;
	}
	
}
