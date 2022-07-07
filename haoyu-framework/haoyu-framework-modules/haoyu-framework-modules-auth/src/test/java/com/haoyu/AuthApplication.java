package com.haoyu;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p>
 * Auth
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-15
 */
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = "com.haoyu")
@ConfigurationPropertiesScan(basePackages={"com.haoyu"})
@MapperScans({
    @MapperScan(value = "com.haoyu", markerInterface = com.haoyu.framework.core.base.BaseMapper.class)
})
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
