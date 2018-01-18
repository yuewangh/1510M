package ${rootPath}.service.${packageName};

import java.util.List;

import ${rootPath}.model.${packageName}.${objectName};

public interface ${objectName}Service {

	//新增
	public int insert(${objectName} ${objectNameLower});

	//修改
	public int update(${objectName} ${objectNameLower});

	//删除
	public int delete(String id);

	//根据Id查询单条记录
	public ${objectName} getById(String id);

	//分页查询列表
	public List<${objectName}> getList(${objectName} ${objectNameLower});
	
	//分页查询数量
	public int getCount(${objectName} ${objectNameLower});
	
	//批量删除
	public int deleteAll(String[] id);
}