<#assign className = table.className>   
package ${basepackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ${basepackage}.dao.${className}Mapper;
import ${basepackage}.service.${className}Service;

@Service(value="${table.lowerClassName}Service")
public class ${className}ServiceImpl implements ${className}Service {

	@Autowired
	private ${className}Mapper ${table.lowerClassName}Mapper;

}
