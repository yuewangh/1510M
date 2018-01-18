package ${rootPath}.service.${packageName}.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${rootPath}.model.${packageName}.${objectName};
import ${rootPath}.dao.${packageName}.${objectName}Dao;
import ${rootPath}.service.${packageName}.${objectName}Service;

@Service
public class ${objectName}ServiceImpl implements ${objectName}Service {

	@Autowired
	private ${objectName}Dao ${objectNameLower}Dao;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(${objectName} ${objectNameLower}) {
		return ${objectNameLower}Dao.insert(${objectNameLower});
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(${objectName} ${objectNameLower}) {
		return ${objectNameLower}Dao.update(${objectNameLower});
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int delete(String id) {
		return ${objectNameLower}Dao.delete(id);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public ${objectName} getById(String id) {
		return ${objectNameLower}Dao.getById(id);
	}
	
	/**
	 * 查询分页列表
	 */
	@Override
	public List<${objectName}> getList(${objectName} ${objectNameLower}) {
		return ${objectNameLower}Dao.getList(${objectNameLower});
	}
	
	//分页查询数量
	public int getCount(${objectName} ${objectNameLower}){
		return ${objectNameLower}Dao.getCount(${objectNameLower});
	}
	
	/**
	 * 删除所有
	 */
	@Override
	public int deleteAll(String[] id) {
		return ${objectNameLower}Dao.deleteAll(id);
	}

}