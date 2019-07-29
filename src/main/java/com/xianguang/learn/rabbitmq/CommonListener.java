package com.xianguang.learn.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xianguang.learn.dto.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


/**
 * @author kongchengguying
 */
@Component
public class CommonListener implements MessageListener
{

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @RabbitListener(queues = "${basic.info.mq.queue.name}",containerFactory = "singleListenerContainer")
    public void onMessage(Message message) {
        try {
            System.out.println(Thread.currentThread().getId()+":"+objectMapper.readValue(message.getBody(), Map.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
