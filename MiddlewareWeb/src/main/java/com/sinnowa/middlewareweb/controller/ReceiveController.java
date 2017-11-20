package com.sinnowa.middlewareweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.model.DSSample;
import com.sinnowa.middlewareweb.service.DSService;
import com.sinnowa.middlewareweb.util.JsonReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ZingBug on 2017/11/18.
 */
@Controller
public class ReceiveController {
    private static final Logger logger=Logger.getLogger(ReceiveController.class);

    @Autowired
    private DSService dsService;

    @RequestMapping(path = "/receive/DS",method = {RequestMethod.GET,RequestMethod.POST},produces = "text/json;charset=UTF-8")
    public void reveiceDSSample(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            JSONObject json= JsonReader.receivePost(request);
            DSSample dsSample=JSONObject.toJavaObject(json,DSSample.class);
            if(dsSample!=null)
            {
                dsService.addSample(dsSample);
                logger.info("接收样本数据成功 "+dsSample.getSampleId()+" "+dsSample.getItem());
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
