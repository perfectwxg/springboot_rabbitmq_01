package com.xianguang.learn.listener;

import com.xianguang.learn.domain.OrderRecord;
import com.xianguang.learn.listener.event.PushOrderRecordEvent;
import com.xianguang.learn.mapper.OrderRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PushOrderRecordListener implements ApplicationListener<PushOrderRecordEvent> {

    @Autowired
    private OrderRecordMapper mapper;

    @Override
    public void onApplicationEvent(PushOrderRecordEvent event) {
        System.out.println("监听到订单"+event.getOrderNo());
        OrderRecord record = new OrderRecord();
        record.setOrderNo(event.getOrderNo());
        record.setOrderType(event.getOrderType());
        mapper.insertSelective(record);
    }
}
