package com.hwjava.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hwjava.springbootmybatisplus.pojo.YunRoleMenus;
import org.apache.ibatis.annotations.Select;

public interface YunRoleMenusMapper extends BaseMapper<YunRoleMenus> {
    int deleteByPrimaryKey(Long id);

    int insert(YunRoleMenus record);

    int insertSelective(YunRoleMenus record);

    YunRoleMenus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YunRoleMenus record);

    int updateByPrimaryKey(YunRoleMenus record);

    @Select("delete from  yun_role_menus where role_id=#{0}")
    void deleteByRoleId(Long roleId);
}