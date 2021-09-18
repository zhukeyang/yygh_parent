package com.atguigu.yyg.hosp.controller;

import com.atguigu.yyg.hosp.service.HospitalService;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin/hosp/hospital")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;
    //医院列表(条件查询分页)
    @GetMapping("list/{page}/{limit}")
    public Result listHosp(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           HospitalQueryVo hospitalQueryVo) {
        Page<Hospital> pageModel = hospitalService.selectHospPage(page,limit,hospitalQueryVo);
        List<Hospital> content = pageModel.getContent();
        long totalElements = pageModel.getTotalElements();

        return Result.ok(pageModel);
    }
    //更新医院的上线状态
    @GetMapping("updateHospStatus/{id}/{status}")
    public Result updateHospStatus(@PathVariable String id,@PathVariable Integer status)
    {
        hospitalService.updateStatus(id,status);
        return Result.ok();
    }
    //医院详情信息
    @GetMapping("showHospDetail/{id}")
    public Result showHospDetail(@PathVariable String id)
    {
        Map<String,Object> map=hospitalService.getHospById(id);
        return Result.ok(map);
    }
    //获取医院地址
    @GetMapping("findAddressById/{id}")
    public Result findAddressById(@PathVariable String id)
    {
        String Address= hospitalService.findAddressById(id);
        System.out.println(Address);
        return Result.ok(Address);
    }

}
