package com.xianguang.learn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CommonMQService {

    private static final Logger log= LoggerFactory.getLogger(CommonMQService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;

    public void sendRobbingMsg(String mobile) {
        try {
            rabbitTemplate.setExchange(env.getProperty("product.robbing.mq.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("product.robbing.mq.routing.key.name"));
            //构造消息
            Message message = MessageBuilder.withBody(mobile.getBytes("UTF-8")).setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.send(message);

        } catch (Exception e) {
            log.error("发送抢单信息入队列 发生异常： mobile={} ",mobile);
        }
    }
}
