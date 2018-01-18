package com.wy.utils;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreemarkerHelp {
	   //用户自定义
		private static String rootPath = "com.wy";//目录
		private static String packageName ="role";//包名				
		private static String tablename = "ztl_role";//表名		
		private static String objectName = "Role";//类名	
		private static String xmname = "wy_app";//controller类名称
		/**
		 * mian方法运行生成代码
		 * @param args
		 * @throws Exception 
		 */
		public static void main(String[] args) throws Exception {
			String remark = "学生";	   			//类名	
			//开始生成
			Map<String,Object> data = new HashMap<String,Object>();		//创建数据模型
			data.put("rootPath", rootPath);
			data.put("fieldList", getFields());
			data.put("remark", remark);						//业务名称
			data.put("packageName", packageName);						//包名
			data.put("objectName", objectName);							//类名
			data.put("objectNameLower", lowerStr(objectName));		//类名(首字母改成 小写)
			data.put("tablename",tablename);								//表前缀	
			data.put("nowDate", new Date());
			createMain(data);
		}
		 /**
		    * 把输入字符串的首字母改成大写
		    * 
		    * @param str
		    * @return
		    */
		    private static String upperStr(String str) {
		        char[] ch = str.toCharArray();
		        if (ch[0] >= 'a' && ch[0] <= 'z') {
		            ch[0] = (char) (ch[0]-32);
		        }
		        return new String(ch);
		    }
		    /**
		     * 把输入字符串的首字母改成 小写
		     * 
		     * @param str
		     * @return
		     */
		    private static String lowerStr(String str) {
		    	char[] ch = str.toCharArray();
		    	if (ch[0] >= 'A' && ch[0] <= 'Z') {
		    		ch[0] = (char) (ch[0]+32);
		    	}
		    	return new String(ch);
		    }
		/**
		 * 获取字段信息
		 * @param tableName表名
		 * @return
		 */
		public static List<String[]> getFields() {
			List<String[]> fieldList = new ArrayList<String[]>();  
			try {
				//获取数据库连接
	            DatabaseMetaData dbmd=DbConUtil.getcon().getMetaData();
	            //获取结果集
	            ResultSet rs = dbmd.getColumns(null, "%", tablename, "%");
	            while(rs.next()) { 
	            	String type = rs.getString("TYPE_NAME");//字段类型
	            	String name = rs.getString("COLUMN_NAME");//字段名称
	            	String remark = rs.getString("REMARKS");//字段注释
	            	String[] field= new String[5];
	            	field[0]=name;
	            	field[2]=remark;
	            	field[4]=upperStr(name);
	        		if(type.equals("DATETIME")){
	        			field[1]="Date";
	            		field[3]="否";
	        		} else	if(type.equals("INT")){
	            			field[1]="Integer";
	                		field[3]="是";
	        		} else{
	        			field[1]="String";
	            		field[3]="是";
	        		}
	            	fieldList.add(field);
	            }
	    		return fieldList;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return fieldList;
	        }
		}
		/**
		 * 生成代码主要方法
		 * @param response
		 * @param type：out生成代码打包导出，否则，生成到代码内部，刷新项目可见
		 * @param packageName 包名
		 * @param objectName  类名
		 * @param tablename 表名
		 * @param data 表字段数据
		 * @throws Exception
		 */
		private static void createMain(Map<String, Object> data) throws Exception {
		    String javaPath = System.getProperty("user.dir")+ "/src/main/java/"+ rootPath.replaceAll("\\.", "/")+"/";//Java包名
			String jsppath =  System.getProperty("user.dir")+ "/src/main/webapp/WEB-INF/"+packageName;//jsp包名
			/*生成model*/
		    Freemarker.printFile("modelTemplate.ftl", data, javaPath.replace("wy_common", "wy_model")+"/model/"+packageName+"/"+objectName+ ".java");
			/*生成controller*/
		    Freemarker.printFile("controllerTemplate.ftl", data, javaPath.replace("wy_common", xmname)+"/controller/"+packageName+"/"+objectName+ "Controller.java");
			/*生成service*/
		    Freemarker.printFile("serviceImplTemplate.ftl", data, javaPath.replace("wy_common", xmname)+"/service/"+packageName+"/impl"+"/"+objectName+"ServiceImpl.java");
		    Freemarker.printFile("serviceTemplate.ftl", data, javaPath.replace("wy_common", "wy_service")+"/service/"+packageName+"/"+objectName+"Service.java");
			/*生成DAO*/
		    Freemarker.printFile("daojavaTemplate.ftl", data,javaPath.replace("wy_common", "wy_dao")+"/dao/"+packageName+"/"+objectName+"Dao.java");
		    Freemarker.printFile("daoxmlTemplate.ftl", data,javaPath.replace("wy_common", "wy_dao")+"/dao/"+packageName+"/"+objectName+"Dao.xml");
			/*生成SQL脚本*/
//		    Freemarker.print("mysql_SQL_Template.ftl", data);  //控制台打印
//		    Freemarker.print("oracle_SQL_Template.ftl", data);  //控制台打印
			/*生成jsp页面*/
//		    Freemarker.printFile("jsp_list_Template.ftl", data,jsppath+"/list.jsp");
//		    Freemarker.printFile("jsp_edit_Template.ftl", data,jsppath+"/edit.jsp");
		}
}
