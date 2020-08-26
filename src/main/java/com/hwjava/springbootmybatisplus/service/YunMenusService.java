package com.hwjava.springbootmybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hwjava.springbootmybatisplus.pojo.YunMenus;
import com.hwjava.springbootmybatisplus.vo.YunMenusVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface YunMenusService extends IService<YunMenus> {

    Set<String> getAllMenus(Long rId);
    Set<String> getAllPermission(Long rId);

    List<YunMenus> getMenusListByRoleId(Long rId);

    List<YunMenusVo> getAllList();

    Map getMenusList();
}
