package com.hwjava.springbootmybatisplus.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ugaoxin.springbootmybatisplus.pojo.YunRole;
import com.ugaoxin.springbootmybatisplus.pojo.YunUser;
import com.ugaoxin.springbootmybatisplus.service.YunRoleService;
import com.ugaoxin.springbootmybatisplus.service.YunUserService;
import com.ugaoxin.springbootmybatisplus.util.YunResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("yun/user")
@RestController
public class UserController {

    @Autowired
    private YunUserService yunUserService;
    @Autowired
    private YunRoleService yunRoleService;


    /**
     * 查询用户和用户所在的角色
     */
    @RequestMapping("list")
    @RequiresPermissions("yun:user:list")
    public YunResult getList(int pageNum,int pageRow) {
        IPage<YunUser> iPage = new Page<>(pageNum,pageRow);
        //只查询没有冻结的用户 ： is_delete=0
        IPage<YunUser> page = yunUserService.page(iPage,new QueryWrapper<YunUser>().lambda().eq(YunUser::getIsDelete ,"0"));
         List list = new ArrayList<>();
        // 查询对应的角色
        for (YunUser yunUser:page.getRecords() ) {
            YunRole yunRole = yunRoleService.getById(yunUser.getRoleId());
            yunUser.setRoleName(yunRole.getRoleName());
            yunUser.setRole(yunRole);
            list.add(yunUser);
        }
        page.setRecords(list);

        return  YunResult.createBySuccess("查询成功！",page);

    }

    /**
     * 添加
     */
    @RequiresPermissions("yun:user:add")
    @RequestMapping("add")
    public YunResult add(@RequestBody JSONObject jsonObject){
        if(jsonObject!=null&&!jsonObject.equals("")){
            String userName = jsonObject.getString("userName");
            String passWord = jsonObject.getString("passWord");
            String realName = jsonObject.getString("realName");
            Long rId =jsonObject.getLong("roleId");

            YunUser yunUser = new YunUser();
            yunUser.setUserName(userName);
            yunUser.setPassWord(passWord);
            yunUser.setIsDelete("0");
            yunUser.setRealName(realName);
            yunUser.setRoleId(rId);
           boolean flag = yunUserService.saveOrUpdate(yunUser);
           return  YunResult.createBySuccess("添加用户成功！",flag);
        }
        return  YunResult.createByError();

    }


    /**
     * 添加
     */
    @RequiresPermissions("yun:user:update")
    @RequestMapping("update")
    public YunResult update(@RequestBody JSONObject jsonObject){
        if(jsonObject!=null&&!jsonObject.equals("")){
            String userName = jsonObject.getString("userName");
            String passWord = jsonObject.getString("passWord");
            String realName = jsonObject.getString("realName");
            Long rId =jsonObject.getLong("roleId");
            Long id = jsonObject.getLong("id");
            YunUser yunUser = new YunUser();
            yunUser.setUserName(userName);
            yunUser.setPassWord(passWord);
            yunUser.setIsDelete("0");
            yunUser.setRealName(realName);
            yunUser.setRoleId(rId);
            yunUser.setId(id);
            boolean flag = yunUserService.saveOrUpdate(yunUser);
            return  YunResult.createBySuccess("更新用户成功！",flag);
        }
        return  YunResult.createByError();

    }


    /**
     * 用户删除接口(冻结)  逻辑删除，相当于更新
     */
    @RequiresPermissions("yun:user:delete")
    @RequestMapping("delete")
    public YunResult delete(@RequestBody JSONObject jsonObject){
        if(jsonObject!=null&&!jsonObject.equals("")){
            String is_delete = jsonObject.getString("isDelete");
            Long id = jsonObject.getLong("id");
            YunUser yunUser = new YunUser();
            yunUser.setIsDelete(is_delete);
            yunUser.setId(id);
            boolean flag = yunUserService.saveOrUpdate(yunUser);
            return  YunResult.createBySuccess("冻结/解冻用户成功！",flag);
        }
        return  YunResult.createByError();

    }

    @RequiresPermissions(value = {"yun:user:add","yun:user:update"},logical = Logical.OR)
    @RequestMapping("getRolelist")
    public YunResult getRolelist(){
        List<YunRole> list = yunRoleService.list();
        return YunResult.createBySuccess("获取权限成功！",list);
    }
}
