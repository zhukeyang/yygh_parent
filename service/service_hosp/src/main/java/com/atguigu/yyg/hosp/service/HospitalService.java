package com.atguigu.yyg.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    void save(Map<String, Object> requestMap);

    //根据医院编号进行查询
    Hospital getByHoscode(String hoscode);
}