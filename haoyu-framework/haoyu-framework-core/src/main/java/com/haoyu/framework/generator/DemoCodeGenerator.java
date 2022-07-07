package com.haoyu.framework.generator;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.haoyu.framework.generator.utils.MyBatisPlusGeneratorUtils;

import java.util.Map;

/**
 * 示例代码生成类
 */
public class DemoCodeGenerator {

    public static void main(String[] args) {

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.0.3:3308/national-train?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDbType(DbType.MYSQL);
        dsc.setUsername("haoyu");
        dsc.setPassword("password");
        // dsc.setSchemaName("public");

        String parent=null;
        String moduleName=null;
        String[] tables=null;
        String[] tablePrefix=null;
        Map<String,String> nameMap=null;

        // 模块1 无需去掉表前缀
//        parent="com.haoyu.nt";
//        moduleName="score";
//        tables=("score_project_target").split(",");
//        MyBatisPlusGeneratorUtils.generatorCode(dsc,parent,moduleName,tables);

        // 模块2 需要去掉表前缀
//        parent="com.haoyu.nt";
//        moduleName="workshop";
//        tables=("eva_module,eva_survey,eva_category,eva_survey_user").split(",");
//        tablePrefix = new String[]{"eva_"};
//        MyBatisPlusGeneratorUtils.generatorCode(dsc, parent, moduleName, tables, tablePrefix);

        parent = "com.haoyu.framework.modules";
        moduleName = "workshop";
        tables = (new String[]{
                "workshop_browsing_history",
        });
        nameMap= MapUtil.newHashMap();
        nameMap.put("workshop_browsing_history","WorkshopBrowsingHistory");
        MyBatisPlusGeneratorUtils.generatorCode(dsc, parent, moduleName, tables, nameMap);


    }



}
