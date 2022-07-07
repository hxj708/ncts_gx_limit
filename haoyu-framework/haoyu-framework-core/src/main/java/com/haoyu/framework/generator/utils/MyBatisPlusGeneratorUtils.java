package com.haoyu.framework.generator.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class MyBatisPlusGeneratorUtils {

    /**
     * 按表名生成实体
     * @param dsc
     * @param parent
     * @param moduleName
     * @param tables
     */
    public static void generatorCode(DataSourceConfig dsc, String parent, String moduleName, String[] tables) {
        generatorCode(dsc, parent, moduleName, tables, null,null);
    }

    /**
     * 按表名生成实体
     * 会根据前缀配置删除前缀
     * @param dsc
     * @param parent
     * @param moduleName
     * @param tables
     * @param tablePrefix
     */
    public static void generatorCode(DataSourceConfig dsc, String parent, String moduleName, String[] tables, String[] tablePrefix) {
        generatorCode(dsc, parent, moduleName, tables, tablePrefix,null);
    }

    /**
     * 按nameMap（key表名：value实体名）配置来生成实体名称
     * @param dsc
     * @param parent
     * @param moduleName
     * @param tables
     * @param nameMap
     */
    public static void generatorCode(DataSourceConfig dsc, String parent, String moduleName, String[] tables, Map<String,String> nameMap) {
        generatorCode(dsc, parent, moduleName, tables, null,nameMap);
    }

    public static void generatorCode(DataSourceConfig dsc, String parent, String moduleName, String[] tables, String[] tablePrefix, Map<String,String> nameMap) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 公共配置 开始-------------------------------
        // 1.全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/doc/temp");
        gc.setAuthor("haoyu-framework-generator");
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setServiceName("%sService");
        gc.setDateType(DateType.ONLY_DATE);
        mpg.setGlobalConfig(gc);

        // 2.数据源配置
        mpg.setDataSource(dsc);

        // 3.设置包路径
        PackageConfig pc = new PackageConfig();
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setXml("mapper.xml");
        pc.setController("web");
        pc.setParent(parent);
        pc.setModuleName(moduleName);
        mpg.setPackageInfo(pc);

        // 4.配置自定义数据
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = MapUtil.newHashMap();
                map.put("packageModules", pc.getParent());
                map.put("packageUtils", pc.getParent() + ".utils");
                this.setMap(map);
            }
        };


        // 自定义 api js 生成
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/generator/templates/api.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return projectPath + "/doc/temp/api/" + pc.getModuleName() + "/" + tableInfo.getName() + ".js";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 自定义 vue 生成
        focList.add(new FileOutConfig("/generator/templates/vue/bms/list_vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return projectPath + "/doc/temp/pages/admin/" + pc.getModuleName() + "/list_" + tableInfo.getName() + ".vue";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        focList.add(new FileOutConfig("/generator/templates/vue/bms/edit_vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return projectPath + "/doc/temp/pages/admin/" + pc.getModuleName() + "/edit_" + tableInfo.getName() + ".vue";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        focList.add(new FileOutConfig("/generator/templates/vue/bms/view_vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return projectPath + "/doc/temp/pages/admin/" + pc.getModuleName() + "/view_" + tableInfo.getName() + ".vue";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        focList.add(new FileOutConfig("/generator/templates/vue/user/list_vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return projectPath + "/doc/temp/pages/user/" + pc.getModuleName() + "/list_" + tableInfo.getName() + ".vue";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        focList.add(new FileOutConfig("/generator/templates/vue/user/edit_vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return projectPath + "/doc/temp/pages/user/" + pc.getModuleName() + "/edit_" + tableInfo.getName() + ".vue";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        focList.add(new FileOutConfig("/generator/templates/vue/user/view_vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return projectPath + "/doc/temp/pages/user/" + pc.getModuleName() + "/view_" + tableInfo.getName() + ".vue";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        //自定义mapper.xml
        focList.add(new FileOutConfig("/generator/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return gc.getOutputDir() + "/"
                    + StrUtil.replace(pc.getParent(), ".", "/") + "/"
                    + "mapper/xml/"
                    + StrUtil.upperFirst(tableInfo.getEntityName()) + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        //自定义entity
        focList.add(new FileOutConfig("/generator/templates/java/entity.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                MyBatisPlusGeneratorUtils.setCustomMap(tableInfo);
                return gc.getOutputDir() + "/"
                    + StrUtil.replace(pc.getParent(), ".", "/") + "/"
                    + "entity/"
                    + StrUtil.upperFirst(tableInfo.getEntityName()) + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 4.配置自定义数据-自定义配置会被优先输出
//        List<FileOutConfig> focList = new ArrayList<>();

        // 4.自定义模板-entityVO-只生成一次
        String additionTemplatePath = "/generator/templates/java/entityVO.java.ftl";
        focList.add(new FileOutConfig(additionTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return gc.getOutputDir() + "/"
                        + StrUtil.replace(pc.getParent(), ".", "/") + "/"
                        + pc.getEntity() + "/"
                        + StrUtil.upperFirst(pc.getModuleName()) + "VO" + StringPool.DOT_JAVA;
            }
        });
        additionTemplatePath = "/generator/templates/java/wrapperUtils.java.ftl";
        focList.add(new FileOutConfig(additionTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return gc.getOutputDir() + "/"
                        + StrUtil.replace(pc.getParent(), ".", "/") + "/"
                        + "utils/"
                        + StrUtil.upperFirst(pc.getModuleName()) + "WrapperUtils" + StringPool.DOT_JAVA;
            }
        });
//        additionTemplatePath = "/generator/templates/java/application.java.ftl";
//        focList.add(new FileOutConfig(additionTemplatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return gc.getOutputDir() + "/"
//                        + StrUtil.replace(pc.getParent(), ".", "/") + "/"
//                        + StrUtil.upperFirst(pc.getModuleName()) + "Application" + StringPool.DOT_JAVA;
//            }
//        });
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                boolean needCreateFile = true;
                // 判断无需再建立的文件
                List<String> noReCreateFileList = CollUtil.newArrayList();
                noReCreateFileList.add(StrUtil.upperFirst(pc.getModuleName()) + "VO" + StringPool.DOT_JAVA);
                noReCreateFileList.add(StrUtil.upperFirst(pc.getModuleName()) + "WrapperUtils" + StringPool.DOT_JAVA);
                noReCreateFileList.add(StrUtil.upperFirst(pc.getModuleName()) + "Application" + StringPool.DOT_JAVA);
                for (String additionFileName : noReCreateFileList) {
                    if (StrUtil.endWith(filePath, additionFileName) && FileUtil.exist(filePath)) {
                        needCreateFile = false;
                        break;
                    } else {
                        FileUtil.mkParentDirs(filePath);
                    }
                }
                return needCreateFile;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 5.配置默认模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 5.配置模板-配置自定义输出模板-指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        templateConfig.setEntity("generator/templates/java/entity.java");
//        templateConfig.setXml("generator/templates/mapper.xml");
        templateConfig.setEntity(null);
        templateConfig.setXml(null);
        templateConfig.setMapper("generator/templates/java/mapper.java");
        templateConfig.setService("generator/templates/java/service.java");
        templateConfig.setServiceImpl("generator/templates/java/serviceImpl.java");
        templateConfig.setController("generator/templates/java/controller.java");
        mpg.setTemplate(templateConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 6.策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 6.策略配置-代码规范
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(false);
        // 6.策略配置-继承公共类
        strategy.setSuperEntityClass(com.haoyu.framework.core.base.BaseEntity.class);
        strategy.setSuperEntityColumns("id", "create_user", "create_time", "update_user", "update_time", "is_deleted", "version");
        strategy.setSuperMapperClass("com.haoyu.framework.core.base.BaseMapper");
        strategy.setSuperServiceClass(com.haoyu.framework.core.base.BaseService.class);
        strategy.setSuperServiceImplClass(com.haoyu.framework.core.base.BaseServiceImpl.class);
        strategy.setSuperControllerClass(com.haoyu.framework.core.base.BaseController.class);
        strategy.setInclude(tables);
        strategy.setTablePrefix(tablePrefix);
        mpg.setStrategy(strategy);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        if(nameMap!=null) {
            strategy.setNameConvert(new INameConvert() {
                @Override
                public String entityNameConvert(TableInfo tableInfo) {
                    String entityName= NamingStrategy.removePrefixAndCamel(tableInfo.getName(),tablePrefix);
                    tableInfo.setConvert(true);
                    for(String tableName:nameMap.keySet()) {
                        if(StrUtil.equalsIgnoreCase(tableName,tableInfo.getName())){
                            entityName=nameMap.get(tableName);
                            break;
                        }
                    }
                    return entityName;
                }
                @Override
                public String propertyNameConvert(TableField field) {
                    field.setConvert(true);
                    return NamingStrategy.underlineToCamel(field.getName());
                }
            });
        }
        // 7.生成
        mpg.execute();

    }

    public static void setCustomMap(TableInfo tableInfo){
        for (TableField field : tableInfo.getFields()){
            if (StrUtil.isNotEmpty(field.getComment())){
                if (field.getComment().contains("{") && field.getComment().contains("}")){
                    Map<String, Object> json = (Map<String, Object>)JSONUtil.parse(field.getComment());
                    if(field.getCustomMap() == null){
                        field.setCustomMap(MapUtil.newHashMap());
                    }
                    if (json.containsKey("dict")){
                        String dictCode = (String) json.get("dict");
                        field.getCustomMap().put("isDict", true);
                        field.getCustomMap().put("dictCode", dictCode);
                    }
                    if (json.containsKey("type")){
                        String type = (String) json.get("type");
                        if ("text".equals(type)){
                            field.getCustomMap().put("isText", true);
                        }else if("editor".equals(type)){
                            field.getCustomMap().put("isEditor", true);
                        }
                    }
                    if (json.containsKey("name")){
                        String name = (String) json.get("name");
                        field.setComment(name);
                    }
                }
            }
        }
    }
}
