<!DOCTYPE html>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/include/header.inc"%>
<html>
<head>
<title>title</title>
<%@include file="/include/csslib.jsp"%>
</head>
<body>
<!-- 列表 -->
<table id="${objectNameLower}table" class="easyui-datagrid" rownumbers=true  fit=true 
        data-options="url:'${r"${rootpath}"}/${objectNameLower}/getdatagrid.action',fitColumns:true,singleSelect:true,pagination:true,toolbar:'#${objectNameLower}toolbar'">   
    <thead>   
        <tr>  
        <#list fieldList as var>
			<th data-options="field:'${var[0]}',width:60,halign:'center'">${var[2]}</th>   
		</#list> 
			<th data-options="field:'id',width:100,formatter:operation,halign:'center',align:'center'">操作</th>   
        </tr>   
    </thead>   
</table>  
<!-- ${objectNameLower}toolbar -->
<!-- 按钮 -->
<div id="${objectNameLower}toolbar" style="padding:3px 10px">
	<a id="btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="reload();">刷新</a>
	<a id="btn" href="javascript:;" class="easyui-linkbutton c5" data-options="iconCls:'icon-add'" onclick="add();">新增</a>
	<span>名称:</span><input id="name" value="" size=10 /> 
	<a href="javascript:query()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
</div>
<!-- 添加/修改对话框 -->
<div id="${objectNameLower}dlg" class="easyui-dialog" closed="true" buttons="#dlg-buttons"  modal=true top="20px">
	 <form id="${objectNameLower}form" action="${r"${rootpath}"}/${objectNameLower}/save.action" method="post"> 
	 	<input class="easyui-validatebox" type="hidden" name="id" id="id"/>
	 	<table style="padding: 20px">
		 	<#list fieldList as var>
		 		<tr>
		   			<td>${var[2]}：</td>
		   			<td><input class="easyui-validatebox" type="text" name="${var[0]}" data-options="required:true" /></td>
		   		</tr>
	 		</#list>
	   	</table>
	</form>  
</div>
<!-- 添加/修改对话框按钮 -->
		<div id="dlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#${objectNameLower}dlg').dialog('close')"
				style="width:90px">取消</a>
		</div>
<%@include file="/include/jslib.jsp"%>
<script type="text/javascript">

function operation(value,row,index){
	return '<a href="javascript:;" onclick="edit('+index+')" style="margin-left:30px">修改</a><a href="javascript:;" style="margin-left:30px" onclick="del(\''+row.id+'\')">删除</a>';  
}
//刷新
function reload(){
	$('#${objectNameLower}table').datagrid('reload'); 
}
//添加信息
function add(){
	$('#${objectNameLower}dlg').dialog('open').dialog('setTitle','新增');
	$('#${objectNameLower}form').form('clear');
}
//查询
function query(){
	$('#${objectNameLower}table').datagrid('load',{
		name: $("#name").val()
	});
}
//进入查询更多
function toquery(){
	$('#searchdlg').dialog('open').dialog('setTitle','查询');
	$('#searchfm').form('clear');
}
//修改
function edit(index){
	$('#${objectNameLower}table').datagrid('selectRow',index);
	var row = $('#${objectNameLower}table').datagrid('getSelected'); 
 	if (row){
	 	$('#${objectNameLower}dlg').dialog('open').dialog('setTitle','编辑');
	 	$('#${objectNameLower}form').form('load',row);//这句话有问题，第一次加载时正确的，第二次就出错了，还保持第一次的数据
 	}
}
//保存
function save(){
	$('#${objectNameLower}form').form('submit',{
	 	onSubmit: function(){
	 		return $(this).form('validate');
	 	},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$('#${objectNameLower}dlg').dialog('close'); 
				$.messager.alert('提示', '操作成功！');
			 	$('#${objectNameLower}table').datagrid('reload');
			} else {
				$.messager.alert('提示',result.msg, 'error');
			}
		}
 	});
}
//删除
function del(id){
	$.messager.confirm('确认', '确认要删除吗', function(res){
		if (res){
			$.ajax({
				 type: "POST",
				 url : "${r"${rootpath}"}/${objectNameLower}/delete.action",
				 data:{
					 "id" : id
				 },
				 success: function(data){
					if(data.success){
						$.messager.alert('提示', '删除成功','info',function(){
							reload();
						});
					} else{
						$.messager.alert('提示', result.msg, 'error');
					}
				 }
			}); 
		}
	});
}
</script>
</body>
</html>