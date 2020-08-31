package com.hwjava.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ugaoxin.springbootmybatisplus.pojo.YunRole;
import com.ugaoxin.springbootmybatisplus.vo.YunRoleVo;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface YunRoleMapper  extends BaseMapper<YunRole> {
    int deleteByPrimaryKey(Long id);

    int insert(YunRole record);

    int insertSelective(YunRole record);

    YunRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YunRole record);

    int updateByPrimaryKey(YunRole record);
    @Results({
            @Result(property="yunUser",column="roleId",many=@Many(select="com.ugaoxin.springbootmybatisplus.mapper.YunUserMapper.getYunUserByRoleId"))
    })
    @Select("SELECT *,id roleId from yun_role")
    List<YunRoleVo> getList();
}