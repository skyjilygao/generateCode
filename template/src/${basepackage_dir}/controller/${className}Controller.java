<#assign className = table.className>   
package ${basepackage}.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ${basepackage}.api.${className}Service;

@Controller
@RequestMapping("${className}")
public class ${className}Controller {

	@Autowired
	private ${className}Service ${table.lowerClassName}Service;


}