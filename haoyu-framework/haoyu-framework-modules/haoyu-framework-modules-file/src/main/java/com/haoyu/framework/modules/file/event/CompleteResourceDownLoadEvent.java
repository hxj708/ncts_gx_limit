package com.haoyu.framework.modules.file.event;

import org.springframework.context.ApplicationEvent;

public class CompleteResourceDownLoadEvent extends ApplicationEvent {

  public CompleteResourceDownLoadEvent(Object downLoad) {
    super(downLoad);
  }

}
