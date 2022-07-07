package ${package.Entity};

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * ${package.Entity}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Data
public class ${package.ModuleName?cap_first}VO implements Serializable {
    private static final long serialVersionUID = 1L;

    <#list config.tableInfoList as entity>
    private ${entity.entityName} ${entity.entityName?uncap_first} = new ${entity.entityName}();
    </#list>
}
