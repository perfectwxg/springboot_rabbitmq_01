package com.xianguang.learn.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.xianguang.learn.domain.UserOrder;
import com.xianguang.learn.dto.UserOrderDto;
import com.xianguang.learn.mapper.UserOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/30.
 */
@Component("userOrderListener")
public class UserOrderListener implements ChannelAwareMessageListener {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            UserOrderDto entity = objectMapper.readValue(body, UserOrderDto.class);
            System.out.println("用户商城下单监听到消息：" + entity);
            UserOrder userOrder = new UserOrder();
            BeanUtils.copyProperties(entity, userOrder);
            userOrder.setStatus(1);
            userOrderMapper.insertSelective(userOrder);
            channel.basicAck(tag, true);
        } catch (Exception e) {
            System.out.println("用户商城下单 发生异常：" + e.fillInStackTrace());
            channel.basicReject(tag, false);
        }


    }
}



























