package ${rootPath}.model.${objectNameLower};

import ${rootPath}.model.BaseModel;
/**
 * ${remark}
 * @author Administrator
 */
public class ${objectName} extends BaseModel{

<#list fieldList as var>
	private String ${var[0]};//${var[2]}
	
</#list>

<#list fieldList as var>
	public void set${var[4]}(String ${var[0]}){
		this.${var[0]}=${var[0]};
	}

	public String get${var[4]}(){
		return ${var[0]};
	}
	
</#list>
}
