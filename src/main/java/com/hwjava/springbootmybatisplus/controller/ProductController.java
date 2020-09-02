package com.hwjava.springbootmybatisplus.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hwjava.springbootmybatisplus.pojo.Product;
import com.hwjava.springbootmybatisplus.pojo.YunUser;
import com.hwjava.springbootmybatisplus.service.ProductService;
import com.hwjava.springbootmybatisplus.util.YunResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("yun/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 查询商品的列表和分页
     */
    @RequiresPermissions("yun:product:list")
    @RequestMapping("/list")
    public YunResult getList(int pageNum,int pageRow){
            IPage<Product> page = new Page<>(pageNum,pageRow);
        IPage<Product> productIPage = productService.page(page);
        return  YunResult.createBySuccess("查询成功！",productIPage);

    }

    /**
     * 新增
     */
    @RequiresPermissions("yun:product:add")
    @RequestMapping("/add")
    public YunResult add(@RequestBody JSONObject jsonObject){
      if(jsonObject!=null&&!jsonObject.equals("")){
          //获取商品的ID
          // 获取商品的名称
          String productName = jsonObject.getString("productName");
          //获取商品的添加时间和修改时间
          // 通过数据库的特性处理 创建时间和修改时间：CURRENT_TIMESTAMP
          Product product = new Product();
          product.setProductName(productName);
          boolean saveOrUpdate = productService.saveOrUpdate(product);

          return  YunResult.createBySuccess(saveOrUpdate);

      }
      return  YunResult.createByError();
    }


    /**
     * 更新
     */
    @RequiresPermissions("yun:product:update")
    @RequestMapping("/update")
    public YunResult update(@RequestBody JSONObject jsonObject){
        if(jsonObject!=null&&!jsonObject.equals("")){
            //获取商品的ID
            Long id = jsonObject.getLong("id");
            // 获取商品的名称
            String productName = jsonObject.getString("productName");
            //获取商品的添加时间和修改时间
            // 通过数据库的特性处理 创建时间和修改时间：CURRENT_TIMESTAMP
            Product product = new Product();
            product.setProductName(productName);
            product.setId(id);
            boolean saveOrUpdate = productService.saveOrUpdate(product);

            return  YunResult.createBySuccess("更新成功！",saveOrUpdate);

        }
        return  YunResult.createByError();
    }

    /**
     * 商品的删除(上架下架，逻辑删除)
     */
    @RequiresPermissions("yun:product:delete")
    @RequestMapping("/delete")
    public YunResult delete(@RequestBody JSONObject jsonObject){
        //逻辑删除，其实是更新 is_delete  0→1
        if(jsonObject!=null&&!jsonObject.equals("")) {
            //获取商品的ID
            Long id = jsonObject.getLong("id");
            String is_delete = jsonObject.getString("isDelete");//0表示上架，1表示下架
            if(is_delete.equals("0")){
                is_delete="1";
            }else {
                is_delete="0";
            }
            Product product = new Product();
            product.setId(id);
            product.setIsDelete(is_delete);
            boolean saveOrUpdate = productService.saveOrUpdate(product);
            return YunResult.createBySuccess("商品上架/下架成功！",saveOrUpdate);

        }
         return YunResult.createByError();

    }
}
