package com.hwjava.springbootmybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hwjava.springbootmybatisplus.mapper.ProductMapper;
import com.hwjava.springbootmybatisplus.pojo.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
