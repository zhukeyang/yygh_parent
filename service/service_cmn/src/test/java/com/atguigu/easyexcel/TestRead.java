package com.atguigu.easyexcel;

import com.alibaba.excel.EasyExcel;

public class TestRead {
    public static void main(String[] args) {
        //读取文件的路径
        String fileName="E:\\excel\\02.xlsx";
        //调用方法实现读写操作
        EasyExcel.read(fileName,UserData.class,new ExcelListener()).sheet().doRead();
    }
}
