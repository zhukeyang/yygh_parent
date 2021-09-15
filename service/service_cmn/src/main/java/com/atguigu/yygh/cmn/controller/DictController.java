package com.atguigu.yygh.cmn.controller;

import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
@Api(tags = "数据字典的接口")
@RequestMapping(value = "/admin/cmn/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    //导入数据字典

    @PostMapping("importData")
    public Result importDict(MultipartFile file)
    {
        dictService.importDictData(file);
        return Result.ok();
    }



    //导出数据字典的接口
    @ApiOperation(value = "导出数据字典的接口")
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response)
    {
        dictService.exportDictData(response);
    }

    //根据数据的id查询子数据列表
    @ApiOperation(value = "根据数据的id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id)
    {
        List<Dict> list =dictService.findChildData(id);
        return Result.ok(list);
    }

    //根据dictcode和value查询
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    //根据value查询
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        String dictName = dictService.getDictName("",value);
        return dictName;
    }
    //根据dict_code查询 省 市
    @ApiOperation(value = "根据dict_code查询 省 市")
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode)
    {
        List<Dict> list=dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }
}
