package com.atguigu.yyg.hosp.service.impl;

import com.atguigu.yyg.hosp.mapper.HospitalSetMapper;
import com.atguigu.yyg.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {


    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper=new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }
}
