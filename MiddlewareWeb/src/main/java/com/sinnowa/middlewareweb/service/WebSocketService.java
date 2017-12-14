package com.sinnowa.middlewareweb.service;

import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.controller.ReceiveController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by ZingBug on 2017/12/2.
 */
@Service
public class WebSocketService {
    private static final Logger logger=Logger.getLogger(WebSocketService.class);

    private static final String monitorSampleUrl="/topic/getRealDSSample";
    private static final String monitorDeviceUrl="/topic/getRealDevice";

    @Autowired
    private SimpMessagingTemplate template;//是Spring-WebSocket内置的一个消息发送工具，可以将消息发送到指定的客户端。

    @Autowired
    private DSService dsService;

    @Autowired
    private DeviceService deviceService;

    /**
     * 发送实时样本数据
     */
    public void sendRealDSSample()
    {
        //找到最新的8条数据
        JSONObject json=dsService.selectNewSample(8);
        template.convertAndSend(monitorSampleUrl,json);
    }

    /**
     * 发送仪器监控数据
     */
    public void sendRealDevice()
    {
        //找到今天的仪器数据
        JSONObject json=deviceService.selectTodaySampleCount();
        template.convertAndSend(monitorDeviceUrl,json);
    }
}
