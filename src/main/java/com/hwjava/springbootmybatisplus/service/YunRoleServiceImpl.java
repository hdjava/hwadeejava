package com.hwjava.springbootmybatisplus.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwjava.springbootmybatisplus.mapper.YunMenusMapper;
import com.hwjava.springbootmybatisplus.mapper.YunRoleMapper;
import com.hwjava.springbootmybatisplus.mapper.YunRoleMenusMapper;
import com.hwjava.springbootmybatisplus.pojo.YunMenus;
import com.hwjava.springbootmybatisplus.pojo.YunRole;
import com.hwjava.springbootmybatisplus.pojo.YunRoleMenus;
import com.hwjava.springbootmybatisplus.vo.YunMenusVo;
import com.hwjava.springbootmybatisplus.vo.YunRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YunRoleServiceImpl extends ServiceImpl<YunRoleMapper, YunRole> implements  YunRoleService{
    @Autowired
    private  YunRoleMapper yunRoleMapper;
    @Autowired
    private YunMenusMapper yunMenusMapper;
    @Autowired
    private YunRoleMenusMapper yunRoleMenusMapper;
    @Override
    public Map getList() {
         //查询
        List<YunRoleVo> yunRoleVoList = yunRoleMapper.getList();
        for (YunRoleVo yunRolevo:yunRoleVoList) {

            //菜单获取根节点
           List<YunMenusVo> menusList =  yunMenusMapper.getMenusListByPid(0L);
            List<Long> checkIdsList = new ArrayList<>();
            List<YunMenusVo> menusVoList = new ArrayList<>();
            for (YunMenusVo yunMenusVo:menusList) {

                //获取权限和前端页面的 复选框回显做准备
                List<YunMenus>  permissionList = yunMenusMapper.getPerMission(yunMenusVo.getId(),yunRolevo.getId());
                if(permissionList!=null&&!permissionList.equals("")){
                    for (YunMenus yunMenus:permissionList) {
                        checkIdsList.add(yunMenus.getId());
                    }
                }
                 yunMenusVo.setPermissionList(permissionList);

                menusVoList.add(yunMenusVo);
            }
            yunRolevo.setCheckedIds(checkIdsList);
            yunRolevo.setYunMenusVo(menusVoList);

        }
        Map map = new HashMap();
        map.put("list",yunRoleVoList);
        return map;
    }

    @Override
    public int setMenus(JSONObject jsonObject) {
        int count =0;
        //vue的 tree的特性 "[100,101,102]"
        String ids = jsonObject.getString("ids");
        Long roleId = jsonObject.getLong("roleId");

        String[] checkIds = ids.substring(1,ids.length()-1).split(",");
        //为了保证选中的一致性
        yunRoleMenusMapper.deleteByRoleId(roleId);
        for (String id:checkIds) {
            if(id!=null&&!id.equals("")){
                Long checkId = Long.valueOf(id.trim()).longValue();
                YunRoleMenus yunRoleMenus = new YunRoleMenus();
                yunRoleMenus.setRoleId(roleId);
                yunRoleMenus.setPermissionId(checkId);
                int i = yunRoleMenusMapper.insert(yunRoleMenus);
                count = count+i;
            }
        }

        return count;
    }
}
