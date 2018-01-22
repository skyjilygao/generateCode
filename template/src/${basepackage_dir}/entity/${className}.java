<#assign className = table.className>   
package ${basepackage}.entity;
${table.include}
public class ${className} implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	<#list table.cols as column>
	<#if column.comment!=''>
	/**
	 * ${column.comment!''}
	 */
	</#if>
	private ${column.javaType} ${column.proName};
	</#list>

	<#list table.cols as column>
	public void set${column.upFirstProName}(${column.javaType} value) {
		this.${column.proName} = value;
	}

	public ${column.javaType} get${column.upFirstProName}() {
		return this.${column.proName};
	}
<#if false>
// 时间类型时，不生成对于时间string方法。如果想生成，改成true即可
	<#if column.javaType=='Date'>
	public String get${column.upFirstProName}Str() {
		return DateUtils.dTS(this.${column.proName},DateUtils.dt);
	}
	</#if>
</#if>
	</#list>
}