package com.hwjava.springbootmybatisplus.set;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/** 
* @version:1.0
* @Description: www.ugaoxin.com
* @author: Array老师  
*/  
 
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    System.out.println("插入方法实体填充");
    setFieldValByName("testDate", new Date(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    System.out.println("更新方法实体填充");
  }
}
