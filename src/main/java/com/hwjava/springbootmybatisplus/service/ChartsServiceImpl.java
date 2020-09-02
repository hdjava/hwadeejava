package com.hwjava.springbootmybatisplus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwjava.springbootmybatisplus.mapper.ChartsMapper;
import com.hwjava.springbootmybatisplus.pojo.Charts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartsServiceImpl extends ServiceImpl<ChartsMapper, Charts> implements  ChartsService {

    @Autowired
    private  ChartsMapper chartsMapper;
    @Override
    public Map getList() {
         //女性的人员
        List<Charts> wList = chartsMapper.getListBySex("女");
       // 男性人员
        List<Charts> mList = chartsMapper.getListBySex("男");

        //女性每月的人数
        List<Integer> wListForCount = new ArrayList<>();
        //男性每月的人数
        List<Integer> mListForCount = new ArrayList<>();
        //每月总人数
        List<Integer> sumListForCount = new ArrayList<>();

        for (Charts wchart:wList) {
            for (Charts mchart:mList) {
                if(wchart.getTime().equals(mchart.getTime())){
                    wListForCount.add(wchart.getCount());
                    mListForCount.add(mchart.getCount());
                    sumListForCount.add(wchart.getCount()+mchart.getCount());
                }
            }

        }
        Map map = new HashMap();
        map.put("wListForCount",wListForCount);
        map.put("mListForCount",mListForCount);
        map.put("sumListForCount",sumListForCount);

        return map;
    }
}
