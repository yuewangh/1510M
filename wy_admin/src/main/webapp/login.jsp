<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("ctx", request.getContextPath());
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.5/themes/default/easyui.css">   
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.5/themes/icon.css">   
<script type="text/javascript" src="${ctx}/jquery-easyui-1.5/jquery-1.10.2.js"></script>   
<script type="text/javascript" src="${ctx}/jquery-easyui-1.5/jquery.easyui.min.js"></script>  
<script type="text/javascript" src="${ctx}/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/jquery-easyui-1.5/validatetools.js"></script>
</head>
<body>
<div style="margin:160px 0 0 470px">
<form id="loginform" method="post">   
<div class="easyui-panel" style="width:100%;max-width:400px;padding:30px 60px;">
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" name="username" style="width:100%;height:40px;padding:12px" data-options="prompt:'Username',iconCls:'icon-man',iconWidth:38,required:true,validType:'username'">
		</div>
		<div style="margin-bottom:20px">
			<input class="easyui-textbox" type="password" name="password" style="width:100%;height:40px;padding:12px" data-options="prompt:'Password',iconCls:'icon-lock',iconWidth:38,required:true,validType:'password'">
		</div>
		<div style="margin-bottom:20px">
			<input type="text" class="easyui-textbox" name="imgcode" style="width: 100px" data-options="required:true" maxlength="6" minlength="6">
			<img title="点击刷新" onclick="loadimg(this)" src="${ctx}/user/getYzm.action">
		</div>
		<div>
			<a href="javascript:;" onclick="login()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px 0px;width:100%;">
				<span style="font-size:14px;">Login</span>
			</a>
		</div>
	</div>
	</form>
</div>
<script type="text/javascript">
function loadimg(obj){
	$(obj).prop("src","${ctx}/user/getYzm.action?"+new Date().getTime());
}
function login(){
	$('#loginform').form('submit', {    
	    url:"${ctx}/login.action",    
	    onSubmit: function(){    
	    	return $('#loginform').form('validate'); 
	    },    
	    success:function(data){    
	       var res = eval("("+data+")");
	       if(res.success){
	    	   alert("成功");
	       } else{
	    	   alert("成功1");
	       }
	    }    
	});  

}
</script>
</body>
</html>