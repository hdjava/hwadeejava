package com.hwjava.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ugaoxin.springbootmybatisplus.pojo.YunUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface YunUserMapper extends BaseMapper<YunUser> {
    int deleteByPrimaryKey(Long id);

    int insert(YunUser record);

    int insertSelective(YunUser record);

    YunUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(YunUser record);

    int updateByPrimaryKey(YunUser record);
    @Select("select * from yun_user ym where ym.role_id=#{0}")
     List<YunUser> getYunUserByRoleId(Long rId);
}