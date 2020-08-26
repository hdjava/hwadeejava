package com.hwjava.springbootmybatisplus.service;

import com.alibaba.fastjson.JSONObject;
import com.hwjava.springbootmybatisplus.code.CodeEnum;
import com.hwjava.springbootmybatisplus.pojo.YunUser;
import com.hwjava.springbootmybatisplus.util.YunResult;
import com.hwjava.springbootmybatisplus.vo.YunUserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录的实现
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private YunMenusService yunMenusService;

    @Override
    public YunResult login(String userName, String passWord) {
        int code = 0;
        JSONObject loginInfo = new JSONObject();
        //1.获取subject 存储用户信息
        Subject currenUser = SecurityUtils.getSubject();
        //2.token登录
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        try {
            currenUser.login(token);
            code=200;
            loginInfo.put("result","success");
        } catch(AuthenticationException e){
            loginInfo.put("result","fail");
        }
          //可以获取session
        Session session = SecurityUtils.getSubject().getSession();
        return YunResult.createByCodeSuccess(code,"执行完毕！",loginInfo);
    }

    @Override
    public YunResult getInfo() {
        //1.获取当前登录的用户
        Session session = SecurityUtils.getSubject().getSession();
          Subject currentUser = SecurityUtils.getSubject();
        YunUser yunUser  = (YunUser) currentUser.getPrincipal();
        //为了保证登录的同时，需要获取当前登录者权限和能访问的菜单
        YunUserVo yunUserVo= new YunUserVo();
        if(yunUser!=null&&!yunUser.equals("")){

           yunUserVo.setId(yunUser.getId());
           yunUserVo.setUpdateTime(yunUser.getUpdateTime());
           yunUserVo.setIsDelete(yunUser.getIsDelete());
           yunUserVo.setRealName(yunUser.getRealName());
           yunUserVo.setCreateTime(yunUser.getCreateTime());
           yunUserVo.setPassWord(yunUser.getPassWord());
           yunUserVo.setRoleId(yunUser.getRoleId());
            //2.获取当前登录的用户权限和菜单
           yunUserVo.setMenuList(yunMenusService.getAllMenus(yunUser.getRoleId()));
           yunUserVo.setPermissionList(yunMenusService.getAllPermission(yunUser.getRoleId()));
           yunUserVo.setUserName(yunUser.getUserName());
            return YunResult.createBySuccess("获取信息成功！",yunUserVo);

        }

           return YunResult.createByErrorCodeMessage(CodeEnum.ERROR_10001.getErrorCode(),"获取信息失败");

    }

    /**
     * 退出接口服务
     * @return
     */
    @Override
    public YunResult loginOut() {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        }catch (Exception e) {
            return  YunResult.createByErrorMessage("退出失败!");
        }

        return YunResult.createBySuccess("退出成功！");
    }
}
