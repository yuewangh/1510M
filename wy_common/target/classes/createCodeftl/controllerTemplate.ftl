package ${rootPath}.controller.${objectNameLower};

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import ${rootPath}.model.${objectNameLower}.${objectName};
import ${rootPath}.service.${objectNameLower}.${objectName}Service;

@RestController
@RequestMapping("/${objectNameLower}")
public class ${objectName}Controller{

	@Autowired
	private ${objectName}Service ${objectNameLower}Service;
	
	
	//查询分页列表
	@RequestMapping("/list")
	public Map<String, Object> list(HttpServletRequest request,${objectName} ${objectNameLower}){
		Map<String, Object> res = new HashMap<String, Object>();
		int count = ${objectNameLower}Service.getCount(${objectNameLower});
		List<${objectName}> ${objectNameLower}list = ${objectNameLower}Service.getList(${objectNameLower});
		res.put("total", count);
		res.put("rows", ${objectNameLower}list);
		return res;
	}
	
	//保存
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,${objectName} ${objectNameLower}){
		Map<String, Object> map = new HashMap<String, Object>();
		String id= ${objectNameLower}.getId();
		if(id != null && !id.equals("")){
			${objectNameLower}Service.update(${objectNameLower});
		} else{
			${objectNameLower}.setId(UUID.randomUUID().toString());
			${objectNameLower}Service.insert(${objectNameLower});
		}
		map.put("success", true);
		return map;
	}

	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request,String id){
		Map<String, Object> map = new HashMap<String, Object>();
		${objectNameLower}Service.delete(id);
		map.put("success", true);
		return map;
	}

	//进入编辑页面
	@RequestMapping("/getOne")
	@ResponseBody
	public ${objectName} edit(HttpServletRequest request,String id){
	 	${objectName} ${objectNameLower} =null;
		if(id != null && !id.equals("")){
			${objectNameLower} = ${objectNameLower}Service.getById(id);
		}
		return ${objectNameLower};
	}

}