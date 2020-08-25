package com.hwjava.springbootmybatisplus.vo;

import com.hwjava.springbootmybatisplus.pojo.YunMenus;

import java.util.List;

public class YunMenusVo extends YunMenus {

    private   List<YunMenusVo> children;
    private List<YunMenus> permissionList;

    public List<YunMenus> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<YunMenus> permissionList) {
        this.permissionList = permissionList;

    }

    public List<YunMenusVo> getChildren() {
        return children;
    }

    public void setChildren(List<YunMenusVo> children) {
        this.children = children;
    }
}
