package com.hwjava.springbootmybatisplus.controller;

import com.alibaba.fastjson.JSONObject;
import com.hwjava.springbootmybatisplus.pojo.YunRole;
import com.hwjava.springbootmybatisplus.service.YunMenusService;
import com.hwjava.springbootmybatisplus.service.YunRoleService;
import com.hwjava.springbootmybatisplus.util.YunResult;
import com.hwjava.springbootmybatisplus.vo.YunMenusVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("yun/role")
@RestController
public class RoleController {

    @Autowired
    private YunRoleService yunRoleService;

    @Autowired
    private YunMenusService yunMenusService;

    /**
     * 查询角色列表
     */
  /*   public YunResult  list(int pageNum,int pageRow){

          List<YunRole> list =  yunRoleService.list();

          return  YunResult.createBySuccess("查询成功！",list);
     }*/

    /**
     * 查询带有菜单的角色列表
     */
    @RequiresPermissions("yun:role:list")
    @RequestMapping("list")
    public  YunResult getList(){
        Map map =  yunRoleService.getList();
        return  YunResult.createBySuccess("查询成功！",map);
    }

    /**
     * 查询的有权限的列表
     */
    @RequiresPermissions("yun:role:list")
    @RequestMapping("getAlllist")
    public  YunResult getAllList(){
        List<YunMenusVo> list = yunMenusService.getAllList();
      return  YunResult.createBySuccess("查询所有成功！",list);
    }

    /**
     * role添加的添加
      */
    @RequiresPermissions( value = {"yun:role:add","yun:role:update"},logical = Logical.OR)
    @RequestMapping("add")
    public YunResult add(@RequestBody JSONObject yunRole){
        if(yunRole!=null&&!yunRole.equals("")) {
            String roleName = yunRole.getString("roleName");
            Long id = yunRole.getLong("id");
            //is_delete通常前端会把0和1变成true 和 flase
            String is_delete = yunRole.getString("isDelete");// "true","false"
             YunRole yunRole1 = new YunRole();
             yunRole1.setId(id);
             yunRole1.setRoleName(roleName);
             if(is_delete!=null&&!is_delete.equals("")){
                 if(is_delete.equals("true")) {
                     yunRole1.setIsDelete("0");//表示逻辑未删除
                 } else {
                     yunRole1.setIsDelete("1");//表示逻辑删除
                 }
             }
            boolean saveOrUpdate = yunRoleService.saveOrUpdate(yunRole1);
             return  YunResult.createBySuccess("执行成功！",saveOrUpdate);

        }

        return YunResult.createByError();
    }

    /**
     * role添加的修改
     */
    @RequiresPermissions("yun:role:update")
    @RequestMapping("update")
   public YunResult update(@RequestBody JSONObject yunRole){
       if(yunRole!=null&&!yunRole.equals("")){
           String roleName = yunRole.getString("roleName");
           Long id = yunRole.getLong("id");
           String is_delete = yunRole.getString("isDelete");
           YunRole yunRole1 = new YunRole();
           yunRole1.setRoleName(roleName);
           yunRole1.setId(id);
           if(is_delete!=null&&!is_delete.equals("")){
             if(is_delete.equals("true")){
                 yunRole1.setIsDelete("0");
             }else {
                 yunRole1.setIsDelete("1");
             }

           }
           boolean saveOrUpdate = yunRoleService.saveOrUpdate(yunRole1);
           return  YunResult.createBySuccess("执行成功！",saveOrUpdate);

       }
           return  YunResult.createByError();

   }

    /**
     * 删除权限
     */
    @RequiresPermissions("yun:role:delete")
    @RequestMapping("delete")
    public YunResult delete(@RequestBody JSONObject yunRole ){
      if(yunRole!=null&&!yunRole.equals("")){
          Long id = yunRole.getLong("yunRole");
          boolean removeById = yunRoleService.removeById(id);
          return YunResult.createBySuccess("删除成功！",removeById);// 物理删除
      }
    return  YunResult.createByError();
    }

    /**
     * 分配菜单权限
     */
    @RequiresPermissions(value = {"yun:role:add","yun:role:update"},logical = Logical.OR)
    @RequestMapping("setMenus")
    public YunResult setMenus(@RequestBody JSONObject jsonObject){
           int i = yunRoleService.setMenus(jsonObject);
           if(i>0){
              return  YunResult.createBySuccess("执行成功！",i);
           }
        return  YunResult.createByError();
    }
}

