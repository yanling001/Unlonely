package com.hongyaoz.unlonelystudyweb.Listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hongyaoz.unlonelystudyapi.sercvice.SeatService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafakLinener {
    @Reference
    SeatService seatService;
    @KafkaListener(topics = "reseat")
    public void onMessage(Integer message){
        //insertIntoDb(buffer);//这里为插入数据库代码
       seatService.setSeatStatus(message);
    }
}
