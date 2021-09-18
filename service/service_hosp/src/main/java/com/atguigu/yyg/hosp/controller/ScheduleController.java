package com.atguigu.yyg.hosp.controller;

import com.atguigu.yyg.hosp.service.ScheduleService;
import com.atguigu.yygh.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/hosp/schedule")
@CrossOrigin
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    //根据医院编号和科室编号 查询排班规则数据
    @GetMapping("getScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getScheduleRule(@PathVariable long page,
                                  @PathVariable long limit,
                                  @PathVariable String hoscode,
                                  @PathVariable String depcode)
    {
        Map<String,Object> map= scheduleService.getRuleSchedule(page,limit,hoscode,depcode);
        return Result.ok(map);
    }
}