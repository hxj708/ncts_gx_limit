package ${package.Service};

import ${superServiceClassPackage};
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity},${table.mapperName}> {

}
</#if>
