package com.hwjava.springbootmybatisplus.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hwjava.springbootmybatisplus.pojo.YunRole;

import java.util.Map;

public interface YunRoleService extends IService<YunRole> {
    Map getList();

    int setMenus(JSONObject jsonObject);
}
