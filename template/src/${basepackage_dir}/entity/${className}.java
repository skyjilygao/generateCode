<#assign className = table.className>   
package ${basepackage}.entity;
${table.include}
public class ${className} implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	<#list table.cols as column>
	private ${column.javaType} ${column.proName};<#if column.comment!=''>//${column.comment!''}</#if>
	</#list>

<#list table.cols as column>
	public void set${column.upFirstProName}(${column.javaType} value) {
		this.${column.proName} = value;
	}

	public ${column.javaType} get${column.upFirstProName}() {
		return this.${column.proName};
	}

	<#if column.javaType=='Date'>
	public String get${column.upFirstProName}Str() {
		return DateUtils.dTS(this.${column.proName},DateUtils.dt);
	}
	
	</#if>
</#list>
}