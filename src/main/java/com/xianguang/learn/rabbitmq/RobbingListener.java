package com.xianguang.learn.rabbitmq;

import com.xianguang.learn.service.CommonMQService;
import com.xianguang.learn.service.ConcurenyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author kongchengguying
 */

public class RobbingListener implements MessageListener {

    private static final Logger log= LoggerFactory.getLogger(RobbingListener.class);

    @Autowired
    private ConcurenyService concurenyService;

    @Override
    @RabbitListener(queues = "${product.robbing.mq.queue.name}")
    public void onMessage(Message message) {
        try{
            String mobile = new String(message.getBody(), "utf-8");
            log.info("监听到抢购订单，开始处理订单{}",mobile);
            //调用抢购，处理抢购业务
            concurenyService.manageRobbing(mobile);
        }catch (Exception e){
            log.info("监听失败{}",e.fillInStackTrace());
        }

    }
}
