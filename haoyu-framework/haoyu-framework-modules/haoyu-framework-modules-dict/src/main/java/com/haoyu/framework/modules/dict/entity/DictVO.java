package com.haoyu.framework.modules.dict.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * com.haoyu.framework.modules.dict.entity
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-18
 */
@Data
public class DictVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private DictEntry dictEntry = new DictEntry();
    private DictType dictType = new DictType();
    private DictRegion dictRegion = new DictRegion();
}
