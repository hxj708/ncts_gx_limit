<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>

    <sql id="BaseColumnSql">
        select
        <#if table.commonFields??><#list table.commonFields as field>t.${field.columnName?lower_case}<#if field_has_next>,</#if></#list></#if>
        <#if table.fields??><#list table.fields as field><#if !(field.customMap['isEditor'])??>,t.${field.columnName?lower_case}</#if></#list></#if>
    </sql>
    <sql id="ExtendColumnSql">
        <#if table.fields??>
            <#list table.fields as field>
                <#if (field.customMap['isEditor'])??>
        <if test="cm.with${field.propertyName?cap_first} != null">
            ,${field.columnName?lower_case}
        </if>
                </#if>
            </#list>
        </#if>
    </sql>
    <sql id="BaseFromSql">
        from ${table.name} t
    </sql>
    <sql id="BaseWhereSql">
        <where>
            <choose>
                <when test="cm.isDeleted != null and cm.isDeleted != ''">
                    and t.is_deleted = ${r'#'}{cm.isDeleted}
                </when>
                <otherwise>
                    and t.is_deleted = 'N'
                </otherwise>
            </choose>
            <if test="cm.ids != null">
                and t.id in
                <foreach collection="cm.ids" open="(" close=")" item="item" separator=",">
                    ${r'#'}{item}
                </foreach>
            </if>
            <#if table.fields??>
                <#list table.fields as field>
                    <#if field.columnName != 'id'>
                        <#if field.propertyName == 'name'>
            <if test="cm.name != null and cm.name != ''">
                and t.name like concat('%', concat(${r'#'}{cm.name}, '%'))
            </if>
            <if test="cm.nameEq != null and cm.nameEq != ''">
                and t.name = ${r'#'}{cm.nameEq}
            </if>
                        <#else>
            <if test="cm.${field.propertyName} != null and cm.${field.propertyName} != ''">
                and t.${field.columnName?lower_case} = ${r'#'}{cm.${field.propertyName}}
            </if>
                        </#if>
                    </#if>
                </#list>
            </#if>
        </where>
    </sql>

    <!-- 根据ID查询单条记录，用于灵活编写复杂SQL, 如子查询、关联查询等，如果是模块简单只需要单表，则调用mybatis plus自带的selectById -->
    <select id="selectByEntityId" resultMap="BaseResultMap" parameterType="java.lang.String">
        <include refid="BaseColumnSql" />
        <#list table.fields as field><#if (field.customMap['isEditor'])??>,${field.columnName?lower_case}</#if></#list>
        <include refid="BaseFromSql" />
        where t.ID = ${r'#'}{id,jdbcType=VARCHAR}
    </select>

    <select id="selectByMap" resultMap="BaseResultMap">
        <include refid="BaseColumnSql" />
        <include refid="ExtendColumnSql" />
        <include refid="BaseFromSql" />
        <include refid="BaseWhereSql" />
    </select>
</mapper>
