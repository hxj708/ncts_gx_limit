package com.haoyu.framework.model;

import lombok.Data;

import java.util.Date;

@Data
public class BaseModel {

  private String id;
  private Date createTime;
  private String createUser;

}
