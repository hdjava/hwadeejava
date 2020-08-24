package com.ugaoxin.springbootmybatisplus.controller;

import com.ugaoxin.springbootmybatisplus.pojo.YunMenus;
import com.ugaoxin.springbootmybatisplus.service.YunMenusService;
import com.ugaoxin.springbootmybatisplus.util.YunResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("yun/menus")
@RestController
public class MenusController {

    @Autowired
    private YunMenusService yunMenusService;

    /**
     * 父子关系的列表
     */

    @RequiresPermissions("yun:menus:list")
    @RequestMapping("list")
    public YunResult list(){
        Map map  = yunMenusService.getMenusList();
        return  YunResult.createBySuccess("执行成功！",map);
    }


    /**
     * 直接传对象的方式，新增和修改
     */
    @RequiresPermissions("yun:menus:add")
    @RequestMapping("add")
    public YunResult add(YunMenus yunMenus){

        boolean saveOrUpdate = yunMenusService.saveOrUpdate(yunMenus);
        return YunResult.createBySuccess("执行成功！",saveOrUpdate);

    }

    @RequiresPermissions("yun:menus:update")
    @RequestMapping("update")
    public YunResult update(YunMenus yunMenus){
        boolean saveOrUpdate = yunMenusService.saveOrUpdate(yunMenus);
        return  YunResult.createBySuccess("更新成功！",saveOrUpdate);
    }


    /**
     * 删除 ，批量
     */
    @RequiresPermissions("yun:menus:delete")
    @RequestMapping("delete")
    public  YunResult delete(String ids){
      //101,102,103,104,901,902
        String[]  idsAll = ids.split(",");
        Boolean flag = false;
        int count = 0;
        for (String id:idsAll) {
            boolean removeById = yunMenusService.removeById(id);
            count++;
            flag=true;
        }
        if(flag){
            return  YunResult.createBySuccess("删除成功！",count);
        } else {
            return  YunResult.createByError();
        }

    }
}
