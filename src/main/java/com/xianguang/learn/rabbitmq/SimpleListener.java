package com.xianguang.learn.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.xianguang.learn.dto.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("simpleListener")
public class SimpleListener implements ChannelAwareMessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag=message.getMessageProperties().getDeliveryTag();
        try {
            byte[] msg=message.getBody();
            User user=objectMapper.readValue(msg,User.class);
            //int i=1/0;
            System.out.println("简单消息监听确认机制监听到消息： "+user);
            channel.basicAck(tag,true);
        }catch (Exception e){
            System.out.println("简单消息监听确认机制发生异常：： "+e.fillInStackTrace());
            channel.basicReject(tag,false);
        }
    }
}
