package com.hwjava.springbootmybatisplus.vo;

import com.hwjava.springbootmybatisplus.pojo.YunMenus;
import com.hwjava.springbootmybatisplus.pojo.YunRoleMenus;

import java.util.List;

public class YunRoleMenusVo  extends YunRoleMenus {

    private List<YunMenusVo> yunMenus;

    private  List<String> children;

    public List<YunMenusVo> getYunMenus() {
        return yunMenus;
    }

    public void setYunMenus(List<YunMenusVo> yunMenus) {
        this.yunMenus = yunMenus;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
