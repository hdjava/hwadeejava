package com.hwjava.springbootmybatisplus.controller;


import com.alibaba.fastjson.JSONObject;
import com.hwjava.springbootmybatisplus.service.LoginService;
import com.hwjava.springbootmybatisplus.util.YunResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("yun/login")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     *登录的方式1，参数传递
     */
    @RequestMapping("/auth1")
    public YunResult login(String userName,String passWord){

        YunResult result = loginService.login(userName, passWord);

        return  result;

    }

    /**
     * 登录的方式2：json字符串或者json对象传递
     * @param jsonObject
     * @return
     *//**/
    @RequestMapping("/auth")
    public YunResult  loginVue(@RequestBody JSONObject jsonObject){
      if(jsonObject!=null&&!jsonObject.equals("")){
          String userName = jsonObject.getString("userName");
          String passWord = jsonObject.getString("passWord");
          YunResult result = loginService.login(userName, passWord);
          return  result;
      }
           return  YunResult.createByError();
    }

    /**
     * 获取当前登录用户信息的接口
     */
    @RequestMapping("/getInfo")
    public YunResult getInfo(){
        return  loginService.getInfo();
    }

    /**
     * 退出当前用户
     */
    @RequestMapping("/logout")
    public YunResult loginOut(){
        YunResult result = loginService.loginOut();
        return  result;
    }
}
