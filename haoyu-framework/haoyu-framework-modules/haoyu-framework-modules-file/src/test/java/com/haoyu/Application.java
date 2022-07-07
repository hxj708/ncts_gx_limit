package com.haoyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.haoyu")
@MapperScan(value = "com.haoyu.framework.modules", markerInterface = com.haoyu.framework.core.base.BaseMapper.class)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
