package com.xianguang.learn;

import com.xianguang.learn.domain.Product;
import com.xianguang.learn.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Tesfdsfs {
    @Autowired
    private ProductMapper productMapper;

    @Test
    public  void test11(){
        Product ss = productMapper.selectByProductNo("product_10010");
//        Product record=new Product();
//        record.setProductNo("华为手机");
//        productMapper.updateTotal(record);
        System.out.println(ss);
    }
}
