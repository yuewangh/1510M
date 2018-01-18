package com.wy.model;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * 分页工具类
 */
public class BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 4994848560358696240L;
	
	// 查询数据库获取总个数
	private int totalCount;
	// 总的页数
	private int pageCount;
	// 每页的条数
	private int pageSize = 10;
	// 当前页数
	private int pageIndex = 1;
	// 开始位置
	private int startPos;
	// 结束位置
	private int endPos;
	// 开始时间
	private String beginTime;
	// 结束时间
	private String endTime;
	
	private String pageStr;		//最终页面显示的底部翻页导航，详细见：getPageStr();
	
	/**
	 * 计算总页数和起始结束值
	 */
	public void calculatePage() {
		// 获取总页数
		if (totalCount % pageSize == 0) {
			pageCount = totalCount / pageSize;
		} else {
			pageCount = totalCount / pageSize + 1;
		}
		startPos = (pageIndex - 1) * pageSize;
		endPos = pageIndex * pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calculatePage();
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getEndPos() {
		return endPos;
	}

	public void setEndPos(int endPos) {
		this.endPos = endPos;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	//获取上一页的方法
	private int getPrevpage() {
		if(pageIndex != 1){
			return pageIndex-1;
		}else{
			return 1;
		}
	}
	//获取上一页的方法
	private int getNextpage() {
		if(pageIndex != pageCount){
			return pageIndex+1;
		}else{
			return pageIndex;
		}
	}
	public String getPageStr() {
		//获取request
		HttpServletRequest request = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest();
		String url = request.getRequestURI();
		//计算总页数
		if (totalCount % pageSize == 0) {
			pageCount = totalCount / pageSize;
		} else {
			pageCount = totalCount / pageSize + 1;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("共"+totalCount+"条记录 当前页"+pageIndex+"/"+pageCount);
		sb.append(" <a href='##' onclick='turnpage(1)'>首页</a>\n");
		sb.append("<a href='##' onclick='turnpage("+getPrevpage()+")'>上一页</a>\n");
		sb.append("<a href='##' onclick='turnpage("+getNextpage()+")'>下一页</a>\n");
		sb.append("<a href='##' onclick='turnpage("+pageCount+")'>尾页</a>\n");
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("function turnpage(page){\n");
		sb.append("\tvar form = document.forms[0];\n");
		sb.append("\tif(form == undefined){\n");
		sb.append("\t\tform = document.createElement(\"form\");\n");
		sb.append("\tdocument.body.appendChild(form);\n");
		sb.append("\t}\n");
		sb.append("\tvar input = document.createElement('input');\n");
		sb.append("\tinput.setAttribute('type', 'hidden');\n");
		sb.append("\tinput.setAttribute('name', 'pageIndex');\n");
		sb.append("\tinput.setAttribute('value', page); \n");
		sb.append("\tform.appendChild(input);\n");
		sb.append("\tform.action =\""+url+"\";\n");
		sb.append("\tform.submit();\n");
		sb.append("}\n");
		sb.append("</script>\n");
		pageStr = sb.toString();
		return pageStr;
	}

}
