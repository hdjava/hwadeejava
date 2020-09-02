package com.hwjava.springbootmybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hwjava.springbootmybatisplus.pojo.Charts;

import java.util.Map;

public interface ChartsService  extends IService<Charts> {
    Map getList();
}
