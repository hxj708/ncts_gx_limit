package com.haoyu.framework.core.base;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.PageUtil;
import com.haoyu.framework.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.StrUtil;

/**
 * @author shibo
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    private final List<String> PAGE_PARAM_NAMES = Arrays.asList("size", "current", "orders");

    /**
     * 获取页面传进来的分页参数
     * 默认第1页，每页10条
     * 多个排序规则用空格隔开，正倒序前用.
     * 举例： {'page':1, 'size':'10', orders:'CREATE_TIME.DESC TITLE'}
     */
    protected IPage getPage(boolean isPage) {
        Page page = new Page<>();
        if (isPage){
            String size = request.getParameter("size");
            if (StrUtil.isNotEmpty(size)) {
                page.setSize(Long.valueOf(size));
            }
            String current = request.getParameter("current");
            if (StrUtil.isNotEmpty(current)) {
                page.setCurrent(Long.valueOf(current));
            }
        }else{
            page.setSearchCount(false);
            page.setCurrent(1);
            page.setSize(Integer.MAX_VALUE);
        }
        String orders = request.getParameter("orders");
        page = PageUtils.setOrders(page, orders);
        return page;
    }

    /**
     * 获取页面传进来的查询条件参数
     * 举例： {'title':'标题'}
     */
    protected Map<String, Object> getParam() {
        //需补充XSS过滤
        Map<String, Object> param = new HashMap<>();
        final Set<String> parameterNames = request.getParameterMap().keySet();
        for (final String name : parameterNames) {
            if (!PAGE_PARAM_NAMES.contains(name)) {
                String[] values = request.getParameterValues(name);
                String value = "";
                if (values != null && values.length == 1) {
                    param.put(name, values[0]);
                } else {
                    param.put(name, value);
                }
            }
        }
        return param;
    }
}
