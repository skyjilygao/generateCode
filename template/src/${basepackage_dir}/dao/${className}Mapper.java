<#assign className = table.className>
<#assign lowerClassName = table.lowerClassName>
        package ${basepackage}.dao;

//import org.springframework.stereotype.Repository;
 import org.apache.ibatis.annotations.Mapper;
 import ${basepackage}.entity.${className};
//@Repository
@Mapper
public interface ${className}Mapper {
 void update(${className} ${lowerClassName});
 void insert(${className} ${lowerClassName});
 ${className} getById(Integer id);
 void delete(Integer id);
}
