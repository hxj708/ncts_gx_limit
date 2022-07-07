package com.haoyu;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.haoyu.framework.generator.utils.MyBatisPlusGeneratorUtils;

import java.util.Map;

public class AuthCodeGenerator {

    public static void main(String[] args) {

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.0.3:3308/dev_center?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDbType(DbType.MYSQL);
        dsc.setUsername("haoyu");
        dsc.setPassword("password");
        // dsc.setSchemaName("public");

        String parent = null;
        String moduleName = null;
        String[] tables = null;
        String[] tablePrefix = null;
        Map<String,String> nameMap=null;

//        parent="com.haoyu.framework.modules1";
//        moduleName="announcement1";
//        tables=("announcement,announcement_relation,announcement_user").split(",");
//        MyBatisPlusGeneratorUtils.generatorCode(dsc,parent,moduleName,tables);

        // 模块1
        /*
        parent = "com.haoyu.framework.modules";
        moduleName = "auth";
        tables = (new String[]{
                "userview",
                "ipanther_module",
                "ipanther_resource",
                "ipanther_role",
                "ipanther_role_resource",
                "ipanther_user_role",
                "ipanther_login_log",
                "ipanther_login_log_stat"
        });
        tablePrefix = new String[]{"ipanther_"};
        MyBatisPlusGeneratorUtils.generatorCode(dsc, parent, moduleName, tables, tablePrefix);*/


        // 模块2 使用nameMap自定义实体名
        /**/
        parent = "com.haoyu.framework.modules";
        moduleName = "auth";
        tables = (new String[]{
                "userview",
                "ipanther_module",
                "ipanther_resource",
                "ipanther_role",
                "ipanther_role_resource",
                "ipanther_user_role",
                "ipanther_login_log",
                "ipanther_login_log_stat"
        });
        nameMap= MapUtil.newHashMap();
        nameMap.put("userview","LoginUser");
        nameMap.put("ipanther_module","Module");
        nameMap.put("ipanther_resource","Resource");
        nameMap.put("ipanther_role","Role");
        nameMap.put("ipanther_role_resource","RoleResource");
        nameMap.put("ipanther_user_role","UserRole");
        nameMap.put("ipanther_login_log","LoginLog");
        nameMap.put("ipanther_login_log_stat","LoginLogStat");
        MyBatisPlusGeneratorUtils.generatorCode(dsc, parent, moduleName, tables, nameMap);

    }


}
