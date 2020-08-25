package com.hwjava.springbootmybatisplus.service;

import com.hwjava.springbootmybatisplus.util.YunResult;

public interface LoginService {

    /**
     * 登录验证
     */
    YunResult login(String userName, String passWord);

    /**
     *获取当前登录者信息
     */
    YunResult getInfo();

    /**
     * 退出登录
     *
     */
    YunResult loginOut();
}
