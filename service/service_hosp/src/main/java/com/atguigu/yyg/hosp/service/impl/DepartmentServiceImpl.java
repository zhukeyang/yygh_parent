package com.atguigu.yyg.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yyg.hosp.repository.DepartmentRepository;
import com.atguigu.yyg.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    //上传科室接口
    @Override
    public void save(Map<String, Object> paramMap) {
        //吧paramMap转换成对象
        String paramMapString = JSONObject.toJSONString(paramMap);
        Department department= JSONObject.parseObject(paramMapString,Department.class);
        Department departmentExist=departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(),department.getDepcode());
        //根据医院编号和科室编号进行判断
        if(departmentExist!=null)
        {
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        }
        else
        {
            department.setUpdateTime(new Date());
            department.setCreateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }
}
