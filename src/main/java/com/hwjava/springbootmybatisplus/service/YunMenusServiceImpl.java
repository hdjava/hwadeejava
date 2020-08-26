package com.hwjava.springbootmybatisplus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwjava.springbootmybatisplus.mapper.YunMenusMapper;
import com.hwjava.springbootmybatisplus.pojo.YunMenus;
import com.hwjava.springbootmybatisplus.vo.YunMenusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class YunMenusServiceImpl extends ServiceImpl<YunMenusMapper, YunMenus> implements YunMenusService {

    @Autowired
    private YunMenusMapper yunMenusMapper;
    /**
     * 通过权限id获取当前用户所有的菜单
     * @param rId
     * @return
     */
    @Override
    public Set<String> getAllMenus(Long rId) {
        return yunMenusMapper.getAllMenus(rId);
    }

    /*
     * 获取当前用户所有的权限
     */

    @Override
    public Set<String> getAllPermission(Long rId) {
        return yunMenusMapper.getAllPermission(rId);
    }

    @Override
    public List<YunMenus> getMenusListByRoleId(Long rId) {
        return yunMenusMapper.getMenusListByRoleId(rId);
    }

    @Override
    public List<YunMenusVo> getAllList() {
        //查询根节点
        List<YunMenusVo> yunMenusVoList = yunMenusMapper.getMenusListByPid(0L);
        List<YunMenusVo> yunMenusVoList1 = new ArrayList<>();
        for ( YunMenusVo yunMenusVo:yunMenusVoList) {
            List<YunMenus> permissionList = yunMenusMapper.getPermissionList(yunMenusVo.getId());
            yunMenusVo.setPermissionList(permissionList);
            yunMenusVoList1.add(yunMenusVo);
        }


        return yunMenusVoList1;
    }

    @Override
    public Map getMenusList() {
        //查询所有根节点
        List<YunMenusVo> yunMenusVoList = yunMenusMapper.getMenusListByPid(0L);
        // 查询所有记录
        List<YunMenusVo> menusList = yunMenusMapper.getList();

        for (YunMenusVo yunMenusVo:yunMenusVoList) {
            List<YunMenusVo> children = new ArrayList<>();
            for (YunMenusVo yunMenus:menusList) {
                 if(yunMenus.getParentId().equals(yunMenusVo.getId())){
                     children.add(yunMenus);
                 }
            }
            yunMenusVo.setChildren(children);
        }
         Map map = new HashMap();
        map.put("menusMap",yunMenusVoList);

        return map;
    }


}
