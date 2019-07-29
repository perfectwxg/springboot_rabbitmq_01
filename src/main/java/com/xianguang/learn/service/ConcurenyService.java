package com.xianguang.learn.service;

import com.xianguang.learn.domain.Product;
import com.xianguang.learn.domain.ProductRobbingRecord;
import com.xianguang.learn.mapper.ProductMapper;
import com.xianguang.learn.mapper.ProductRobbingRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ConcurenyService {
    private static final Logger log= LoggerFactory.getLogger(ConcurenyService.class);
    @Autowired
    private ProductMapper productMapper;

    /**
     * 抢购商品编号
     */
    private static final String PRODUCT_NO="product_10010";

    @Autowired
    private ProductRobbingRecordMapper recordMapper;

    /**
     * 处理抢购
     * @param mobile
     */
    public void manageRobbing(String mobile){
        try{
            //查询商品
            Product product = productMapper.selectByProductNo(PRODUCT_NO);
            //判断库存
            Integer total = product.getTotal();
            //库存足够
            if (total>0){
                //抢购商品库存减少
                productMapper.updateTotal(product);
                //将信息抢购成功用户信息入库
                ProductRobbingRecord record=new ProductRobbingRecord();
                record.setMobile(mobile);
                recordMapper.insertSelective(record);
                //  发送信息 抢购成功
                log.info("恭喜您,{},抢购成功",mobile);
            }else{
                log.info("库存不足，抢购失败，{}",mobile);
            }
        }catch (Exception e){
            log.info("抢购异常{},{}",mobile,e.fillInStackTrace());
        }

    }

}
