package com.hwjava.springbootmybatisplus.controller;

import com.hwjava.springbootmybatisplus.service.ChartsService;
import com.hwjava.springbootmybatisplus.util.YunResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("yun/charts/")
@RestController
public class ChartsController {

    @Autowired
    private ChartsService chartsService;

    /**
     * 图形列表的输出
     * 2018年 网站下单量 每月 男性的人数，女性的人数，每个月的男女总人数
     */

    @RequiresPermissions("yun:charts:list")
    @RequestMapping("list")
    public YunResult getChart(){
        // 男性的人数，女性的人数，每个月的男女总人数
        Map map = chartsService.getList();
        return  YunResult.createBySuccess("查询图表数据成功！",map);
    }
}
