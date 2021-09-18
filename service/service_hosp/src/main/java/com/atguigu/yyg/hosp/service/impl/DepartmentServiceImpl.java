package com.atguigu.yyg.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yyg.hosp.repository.DepartmentRepository;
import com.atguigu.yyg.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public org.springframework.data.domain.Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        //创建Pageable对象，设置当前页，每页记录数
        //0是第一页
        Pageable pageable= PageRequest.of(page,limit);
        //创建Example对象
        Department department=new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        department.setIsDeleted(0);
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Department> example=Example.of(department,matcher);
        Page<Department> all = departmentRepository.findAll(example, pageable);
        return all;
    }

    //删除医院接口
    @Override
    public void remove(String hoscode, String depcode) {
        //根据医院编号 和 科室编号 查询信息
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department!=null)
        {
            //调用方法
            departmentRepository.deleteById(department.getId());
        }
    }

    //根据医院编号查询科室信息
    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建list集合，用于最终的数据封装
        List<DepartmentVo> result=new ArrayList<>();
        //根据医院编号，查询所有的科室信息
        Department departmentQuery=new Department();
        departmentQuery.setHoscode(hoscode);
        Example example = Example.of(departmentQuery);
        //所有科室列表信息
        List<Department> departmentList = departmentRepository.findAll(example);
        //根据大科室编号(bigcode)分组,获取每个大科室的下级科室
        //java8新特性
        Map<String, List<Department>> departmentMap = departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        //遍历map集合
        for (Map.Entry<String,List<Department>> entry  : departmentMap.entrySet()) {
            //大科室的编号
            String bigcode = entry.getKey();
            //大科室编号对应的全局数据
            List<Department> departmentList1 = entry.getValue();
            System.out.println(departmentList1);
            //封装大科室
            DepartmentVo departmentVo1=new DepartmentVo();
            departmentVo1.setDepcode(bigcode);
            departmentVo1.setDepname(departmentList1.get(0).getBigname());

            //封装小科室
            List<DepartmentVo> children=new ArrayList<>();
            for (Department department : departmentList) {
                DepartmentVo departmentVo2 = new DepartmentVo();
                departmentVo2.setDepcode(department.getDepcode());
                departmentVo2.setDepname(department.getDepname());
                //封装到List集合中
                children.add(departmentVo2);
            }
            //把小科室的list集合放到大科室的children中
            departmentVo1.setChildren(children);
            //放到最终的result中
            result.add(departmentVo1);
        }
        return result;
    }

    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department!=null)
        {
            return department.getDepname();
        }
        return null;
    }
}
