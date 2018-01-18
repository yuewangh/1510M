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
<title>金融运营管理平台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.5/themes/default/easyui.css">   
<link rel="stylesheet" type="text/css" href="${ctx}/jquery-easyui-1.5/themes/icon.css">   
<script type="text/javascript" src="${ctx}/jquery-easyui-1.5/jquery-1.10.2.js"></script>   
<script type="text/javascript" src="${ctx}/jquery-easyui-1.5/jquery.easyui.min.js"></script>  
</head>
<body class="easyui-layout">   
    <div data-options="region:'north',title:'North Title',split:true" style="height:100px;"></div>   
    <div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>   
    <div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>   
    <div data-options="region:'west',title:'West',split:true" style="width:100px;"></div>   
    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;"></div>   
</body>  
</html>