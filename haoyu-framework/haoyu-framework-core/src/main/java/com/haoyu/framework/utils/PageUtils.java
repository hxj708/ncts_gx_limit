package com.haoyu.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageUtils {

  public static Page setOrders(Page page, String orders){
    if (StrUtil.isNotEmpty(orders)) {
      List<OrderItem> orderItems = new ArrayList<>();
      List<String> orderList = Arrays.asList(orders.split(" "));
      for (String or : orderList) {
        //过滤关键字
        or = StrUtil.replace(or, "select", "");
        or = StrUtil.replace(or, "insert", "");
        or = StrUtil.replace(or, "update", "");
        or = StrUtil.replace(or, "delete", "");
        or = StrUtil.replace(or, "drop", "");
        or = StrUtil.replace(or, "truncate", "");
        or = StrUtil.replace(or, "declare", "");
        or = StrUtil.replace(or, "xp_cmdshell", "");
        or = StrUtil.replace(or, "/add", "");
        or = StrUtil.replace(or, "net user", "");
        //去除执行存储过程的命令关键字
        or = StrUtil.replace(or, "exec", "");
        or = StrUtil.replace(or, "execute", "");
        //去除系统存储过程或扩展存储过程关键字
        or = StrUtil.replace(or, "xp_", "x p_");
        or = StrUtil.replace(or, "sp_", "s p_");
        //防止16进制注入
        or = StrUtil.replace(or, "0x", "0 x");

        List<String> order = Arrays.asList(or.split("\\."));
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(order.get(0));
        orderItem.setAsc(true);
        if (order.size() > 1) {
          if (StrUtil.equals("DESC", order.get(1)) || StrUtil.equals("desc", order.get(1))) {
            orderItem.setAsc(false);
          }
        }
        orderItems.add(orderItem);
      }
      page.setOrders(orderItems);
    }
    return page;
  }

}
