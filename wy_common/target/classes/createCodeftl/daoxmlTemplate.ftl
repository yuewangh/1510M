<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${rootPath}.dao.${packageName}.${objectName}Dao">
	
	
	<!-- 新增-->
	<insert id="insert" parameterType="${rootPath}.model.${packageName}.${objectName}">
		insert into ${tablename}(
	<#list fieldList as var>
		<#if var_has_next>
			${var[0]},	
		<#else>
		    ${var[0]}
		</#if>
	</#list>
		) values (
	<#list fieldList as var>
		<#if var_has_next>
			${r"#{"}${var[0]}${r"}"},	
		<#else>
		    ${r"#{"}${var[0]}${r"}"}
		</#if>			
	</#list>
		)
	</insert>
	
	
	<!-- 删除-->
	<delete id="delete" parameterType="String">
		delete from ${tablename} where  id = ${r"#{"}id${r"}"}
	</delete>
	
	
	<!-- 修改 -->
	<update id="update" parameterType="${rootPath}.model.${packageName}.${objectName}">
		update  ${tablename}
	    	 <set> 
		<#list fieldList as var>
			<#if var[3] == "是">
				<if test="${var[0]} != null and ${var[0]} != '' ">
					${var[0]} = ${r"#{"}${var[0]}${r"}"},
				</if>
			</#if>
		</#list>
				<if test="last_update_time != null and last_update_time != '' ">
					last_update_time = ${r"#{"}last_update_time${r"}"},
				</if>
			</set>
		where  id = ${r"#{"}id${r"}"}
	</update>
	
	
	<!-- 通过ID获取数据 -->
	<select id="getById" resultType="${rootPath}.model.${packageName}.${objectName}" parameterType="String">
		select 
	<#list fieldList as var>
		<#if var_has_next>
			a.${var[0]},	
		<#else>
		    a.${var[0]}
		</#if>
	</#list>
		from  ${tablename} a where id = ${r"#{"}id${r"}"}
	</select>
	
	<!-- 列表(分页) -->
	<select id="getList" resultType="${rootPath}.model.${packageName}.${objectName}" parameterType="${rootPath}.model.${packageName}.${objectName}">
		select
	<#list fieldList as var>
		<#if var_has_next>
			<#if var[1] == 'Date'>
			   DATE_FORMAT( a.${var[0]},'%Y-%m-%d %H:%i:%s')  ${var[0]},
			<#else>
			   a.${var[0]},
		 	</#if>
		<#else>
			<#if var[1] == 'Date'>
			   DATE_FORMAT( a.${var[0]},'%Y-%m-%d %H:%i:%s')  ${var[0]}
			<#else>
			   a.${var[0]}
		 	</#if>
		</#if>
	</#list>
		from 
				${tablename} a
		where 1=1
		order by id desc limit ${r"#{"}startPos${r"}"},${r"#{"}pageSize${r"}"}
	</select>
	
	<!-- 分页查询数量-->
	<select id="getCount" resultType="int" parameterType="${rootPath}.model.${packageName}.${objectName}">
		select count(1) from ${tablename} where 1=1
	</select>
	
	<!-- 批量删除 -->
	<delete id="deleteAll" parameterType="String">
		delete from ${tablename}
		where 
			id in
		<foreach item="item" index="index" collection="array" open="(" separator="," close=")">
                 ${r"#{item}"}
		</foreach>
	</delete>
	
</mapper>