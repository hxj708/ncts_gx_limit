package com.haoyu;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * Dict
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-18
 */
@SpringBootApplication(scanBasePackages = "com.haoyu")
@MapperScans({
    @MapperScan(value = "com.haoyu", markerInterface = com.haoyu.framework.core.base.BaseMapper.class)
})
public class DictApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictApplication.class, args);
    }

}
