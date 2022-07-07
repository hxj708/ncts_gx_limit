package ${cfg.packageModules};

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * ${package.ModuleName?cap_first}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@SpringBootApplication(scanBasePackages = "com.haoyu")
@MapperScans({
    @MapperScan(value = "com.haoyu", markerInterface = com.haoyu.framework.core.base.BaseMapper.class)
})
public class ${package.ModuleName?cap_first}Application {

    public static void main(String[] args) {
        SpringApplication.run(${package.ModuleName?cap_first}Application.class, args);
    }

}
