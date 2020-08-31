package com.hwjava.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ugaoxin.springbootmybatisplus.pojo.Charts;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChartsMapper extends BaseMapper<Charts> {
    int deleteByPrimaryKey(Long id);

    int insert(Charts record);

    int insertSelective(Charts record);

    Charts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Charts record);

    int updateByPrimaryKey(Charts record);

    @Select("select * from t_charts th where th.sex=#{0} order by time")
    List<Charts> getListBySex(String sex);
}