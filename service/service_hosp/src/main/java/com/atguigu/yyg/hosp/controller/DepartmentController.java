package com.atguigu.yyg.hosp.controller;

import com.atguigu.yyg.hosp.service.DepartmentService;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/hosp/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    //根据医院编号，查询医院所有科室列表
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode)
    {
        List<DepartmentVo> list=departmentService.findDeptTree(hoscode);
        return Result.ok(list);

    }
}
