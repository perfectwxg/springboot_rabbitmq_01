package com.xianguang.learn.controller;

import com.xianguang.learn.common.BaseResponse;
import com.xianguang.learn.common.StatusCode;
import com.xianguang.learn.listener.PushOrderRecordListener;
import com.xianguang.learn.listener.event.PushOrderRecordEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderRecordController {

    @Autowired
    private ApplicationEventPublisher listener;

    @RequestMapping("/push")
    public BaseResponse pushOrderRecord(){
        PushOrderRecordEvent event = new PushOrderRecordEvent(this,"orderNo_20190821001","物流13");
        listener.publishEvent(event);
        return new BaseResponse(StatusCode.Success);
    }
}
