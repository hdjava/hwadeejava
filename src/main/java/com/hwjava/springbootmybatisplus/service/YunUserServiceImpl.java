package com.hwjava.springbootmybatisplus.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwjava.springbootmybatisplus.mapper.YunUserMapper;
import com.hwjava.springbootmybatisplus.pojo.YunUser;
import org.springframework.stereotype.Service;

@Service
public class YunUserServiceImpl extends ServiceImpl<YunUserMapper, YunUser>  implements  YunUserService{
}
