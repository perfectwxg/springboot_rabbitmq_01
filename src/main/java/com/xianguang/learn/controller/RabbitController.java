package com.xianguang.learn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.xianguang.learn.common.BaseResponse;
import com.xianguang.learn.common.StatusCode;
import com.xianguang.learn.dto.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author kongchengguying
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/sendSimpleMessage",method = RequestMethod.GET)
    public BaseResponse sendSimpleMessage(String message){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        //设置消息转换格式
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置消息队列的交换器
        rabbitTemplate.setExchange(env.getProperty("basic.info.mq.exchange.name"));
        //设置消息队列路由建
        rabbitTemplate.setRoutingKey(env.getProperty("basic.info.mq.routing.key.name"));
        //构造消息
        Message msg= MessageBuilder.withBody(message.getBytes()).build();
        System.out.println(message);
        rabbitTemplate.send(msg);
        return response;
    }

    @RequestMapping(value = "/sendObjectMessage",method = RequestMethod.POST)
    public BaseResponse sendObjectMessage(@RequestBody User user){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        //设置消息转换格式
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置消息队列的交换器
        rabbitTemplate.setExchange(env.getProperty("basic.info.mq.exchange.name"));
        //设置消息队列路由建
        rabbitTemplate.setRoutingKey(env.getProperty("basic.info.mq.routing.key.name"));
        //构造消息
        rabbitTemplate.convertAndSend(user, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                MessageProperties messageProperties=message.getMessageProperties();
                messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,User.class);
                return message;
            }
        });

        return response;
    }

    @RequestMapping(value = "/send/multi/message")
    public BaseResponse sendMultiMessage() throws JsonProcessingException {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        //设置消息转换格式
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置交换器
        rabbitTemplate.setExchange(env.getProperty("basic.info.mq.exchange.name"));
        //设置路由
        rabbitTemplate.setRoutingKey(env.getProperty("basic.info.mq.routing.key.name"));
//        构造消息
        Integer id=120;
        String name="阿修罗";
        Long longId=12000L;
        Map<String,Object> hashMap = Maps.newHashMap();
        hashMap.put("id",id);
        hashMap.put("longId",longId);
        hashMap.put("name",name);
        Message message =MessageBuilder.withBody(objectMapper.writeValueAsBytes(hashMap)).build();
        rabbitTemplate.send(message);
        return response;
    }
}
