package com.haoyu.framework.core.base;

import cn.hutool.json.JSONUtil;
import com.haoyu.framework.utils.JsonMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * WebService 返回数据的标准结构
 *
 * @author liuchao
 */
@Data
public class ResponseData implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer CODE_200 = 200;
    public static final Integer CODE_400 = 400;
    public static final Integer CODE_500 = 500;
    public static final Integer CODE_501 = 501;
    public static final Integer CODE_502 = 502;
    public static final Integer CODE_503 = 503;
    public static final Integer CODE_509 = 504;

    public static final String MESSAGE_200 = "操作成功";
    public static final String MESSAGE_400 = "参数有误";
    public static final String MESSAGE_500 = "操作失败";
    public static final String MESSAGE_501 = "验证ID或密码不正确";
    public static final String MESSAGE_502 = "用户ID和回话ID不能为空";
    public static final String MESSAGE_503 = "无关联培训机构";
    public static final String MESSAGE_509 = "出现错误：";

    /** 总条数 */
    protected Long totalCount = 0L;
    /** 总页数 */
    protected Integer totalPage = 0;
    /** 当前页 */
    protected Integer pageNo = 1;
    /** 每页条数 */
    protected Integer pageSize = 1;

    /** 响应代码 */
    protected Integer responseCode;
    /** 响应代码说明 */
    protected String responseMessage;
    /** 响应数据 */
    protected Map<String, Object> responseData = new HashMap<String, Object>();

    public ResponseData() {

    }

    public ResponseData(Integer responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public static ResponseData defaultEntity() {
        ResponseData entity = new ResponseData(CODE_200, MESSAGE_200);
        entity.setTotalCount(0L);
        entity.setTotalPage(0);
        entity.setPageNo(0);
        entity.setPageSize(0);
        return entity;
    }

    public static ResponseData defaultEntity(Integer pageNo, Integer pageSize) {
        ResponseData entity = new ResponseData(CODE_200, MESSAGE_200);
        entity.setTotalCount(0L);
        entity.setTotalPage(0);
        entity.setPageNo(pageNo <= 0 ? 1 : pageNo);
        entity.setPageSize(pageSize <= 0 ? 10 : (pageSize > 1000 ? 1000 : pageSize));
        return entity;
    }

    public void setResponseStatus(Integer responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String toJson() {
        return JsonMapper.defaultMapper().toJson(this);
    }

}