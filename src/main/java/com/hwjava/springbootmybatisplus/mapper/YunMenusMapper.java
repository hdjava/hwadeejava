package com.hwjava.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwjava.springbootmybatisplus.pojo.YunMenus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface YunMenusMapper extends BaseMapper<YunMenus> {
    int deleteByPrimaryKey(Long id);

    int insert(YunMenus record);

    int insertSelective(YunMenus record);

    YunMenus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YunMenus record);

    int updateByPrimaryKey(YunMenus record);

    @Select("select ym.menu_code from yun_menus ym ,yun_role_menus yrm where ym.id=yrm.permission_id and yrm.role_id=#{0}")
    Set<String> getAllMenus(Long rId);

    @Select("select ym.permission_code from yun_menus ym ,yun_role_menus yrm where ym.id=yrm.permission_id and yrm.role_id=#{0}")
    Set<String> getAllPermission(Long rId);

    @Select("select * from yun_menus ym ,yun_role_menus yrm where ym.id=yrm.permission_id and yrm.role_id=#{0}")
    List<YunMenus> getMenusListByRoleId(Long rId);

    @Select("SELECT *  from yun_menus ym  where ym.parent_id=#{0}")
    List<YunMenusVo> getMenusListByPid(Long pId);
    @Select("select  ym.id,ym.menu_name,ym.parent_id,ym.menu_code,ym.permission_name from yun_menus ym,yun_role_menus  yrm" +
            " where  ym.id=yrm.permission_id and  ym.parent_id=#{pId} and yrm.role_id=#{rId} order by ym.id")
    List<YunMenus> getPerMission(@Param("pId") Long pId, @Param("rId") Long rId);
    @Select("select  * from yun_menus ym where ym.parent_id=#{0}")
    List<YunMenus> getPermissionList(Long id);

    @Select("select * from yun_menus ym")
    List<YunMenusVo> getList();
}