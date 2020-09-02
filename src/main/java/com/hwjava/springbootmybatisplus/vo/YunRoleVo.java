package com.hwjava.springbootmybatisplus.vo;

import com.hwjava.springbootmybatisplus.pojo.YunMenus;
import com.hwjava.springbootmybatisplus.pojo.YunRole;
import com.hwjava.springbootmybatisplus.pojo.YunRoleMenus;
import com.hwjava.springbootmybatisplus.pojo.YunUser;

import java.util.List;

public class YunRoleVo extends YunRole {
    private List<YunUser> yunUser;
    private List<YunRoleMenusVo> yunRoleMenus;
    private List<YunMenusVo> yunMenusVo;
    private List<Long> checkedIds;

    public List<Long> getCheckedIds() {
        return checkedIds;
    }

    public void setCheckedIds(List<Long> checkedIds) {
        this.checkedIds = checkedIds;
    }

    public List<YunUser> getYunUser() {
        return yunUser;
    }

    public void setYunUser(List<YunUser> yunUser) {
        this.yunUser = yunUser;
    }

    public List<YunRoleMenusVo> getYunRoleMenus() {
        return yunRoleMenus;
    }

    public void setYunRoleMenus(List<YunRoleMenusVo> yunRoleMenus) {
        this.yunRoleMenus = yunRoleMenus;
    }

    public List<YunMenusVo> getYunMenusVo() {
        return yunMenusVo;
    }

    public void setYunMenusVo(List<YunMenusVo> yunMenusVo) {
        this.yunMenusVo = yunMenusVo;
    }
}
