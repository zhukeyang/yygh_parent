package com.atguigu.yyg.hosp.controller.api;

import com.atguigu.yyg.hosp.service.DepartmentService;
import com.atguigu.yyg.hosp.service.HospitalService;
import com.atguigu.yyg.hosp.service.HospitalSetService;
import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.common.result.ResultCodeEnum;
import com.atguigu.yygh.common.utils.MD5;
import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    //查询医院的接口
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request)
    {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来医院编号，查询数据库，查询签名
        String signKey=hospitalSetService.getSignKey(hoscode);
        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断签名是否一致
        if(hospSign.equals(signKeyMd5))
        {
            //不一样
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service方法实现医院编号查询
        Hospital hospital=hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }
    //上传医院的接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request)
    {
        //获取到传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //1.获取医院系统传递过来的签名,签名进行了MD5加密
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来医院编号，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey=hospitalSetService.getSignKey(hoscode);
        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断签名是否一致
        if(hospSign.equals(signKeyMd5))
        {
            //不一样
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //得到logoData
        String logoData = (String)paramMap.get("logoData");
        logoData=logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);
        hospitalService.save(paramMap);
        return Result.ok();
    }

    //上传科室接口
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request)
    {
        //获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来医院编号，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey=hospitalSetService.getSignKey(hoscode);
        //3.把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //判断签名是否一致
        if(hospSign.equals(signKeyMd5))
        {
            //不一样
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        //调用service中的方法
        departmentService.save(paramMap);
        return Result.ok();
    }


}
