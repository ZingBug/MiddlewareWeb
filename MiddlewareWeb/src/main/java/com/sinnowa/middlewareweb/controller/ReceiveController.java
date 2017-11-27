package com.sinnowa.middlewareweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.model.DSSample;
import com.sinnowa.middlewareweb.model.Device;
import com.sinnowa.middlewareweb.service.DSService;
import com.sinnowa.middlewareweb.service.DeviceService;
import com.sinnowa.middlewareweb.util.JsonReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ZingBug on 2017/11/18.
 */
@Controller
public class ReceiveController {
    private static final Logger logger=Logger.getLogger(ReceiveController.class);

    @Autowired
    private DSService dsService;

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(path = "/receive/DS",method = {RequestMethod.GET,RequestMethod.POST},produces = "text/json;charset=UTF-8")
    public void reveiceDSSample(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String json=JsonReader.receivePost2String(request);
            List<DSSample> list= JSON.parseArray(json, DSSample.class);//一列样本中，可认为是一个仪器检验的
            if(!list.isEmpty())
            {
                deviceService.updateResult(list.get(0));//将第一条样本数据当作仪器标识
                for(DSSample dsSample:list)
                {
                    dsService.addSample(dsSample);
                    logger.info("接收样本数据成功 "+dsSample.getSampleId()+" "+dsSample.getItem());
                }
            }
            else
            {
                logger.error("接收生化数据出错");
            }
            /*后续可以在response中加些回应信息*/
        }catch (IOException e)
        {
            logger.error("接收生化数据出错 "+e.getMessage());
        }
    }
}
