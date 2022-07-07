package com.haoyu.framework.modules.dict.web;

import com.haoyu.framework.core.base.BaseController;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.dict.entity.DictEntry;
import com.haoyu.framework.modules.dict.entity.DictVO;
import com.haoyu.framework.modules.dict.service.DictEntryService;
import com.haoyu.framework.modules.dict.utils.DictUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("dictController")
@RequestMapping({"/api/v1/dict","/cms/api/v1/dict"})
public class DictController extends BaseController {

    @GetMapping("list")
    public R list(String dictTypeCode) {
        return R.success().setData(DictUtils.getEntryList(dictTypeCode));
    }

    @GetMapping("listByParentValue")
    public R listByParentValue(String dictTypeCode,String parentValue) {
        return R.success().setData(DictUtils.getEntryListByParentValue(dictTypeCode,parentValue));
    }

    @GetMapping("map")
    public R map(String dictTypeCode) {
        return R.success().setData(DictUtils.getEntryMap(dictTypeCode));
    }

    @GetMapping("entry")
    public R entry(String dictTypeCode, String dictValue) {
        return R.success().setData(DictUtils.getEntry(dictTypeCode, dictValue));
    }

    @GetMapping("getName")
    public R getName(String dictTypeCode, String dictValue) {
        return R.success().setData(DictUtils.getEntryName(dictTypeCode, dictValue));
    }

    @GetMapping("getValue")
    public R getValue(String dictTypeCode, String dictName) {
        return R.success().setData(DictUtils.getEntryValue(dictTypeCode, dictName));
    }

}
