package com.sinnowa.middlewareweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.model.DSSample;
import com.sinnowa.middlewareweb.service.DSService;
import com.sinnowa.middlewareweb.util.JsonReader;
import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ZingBug on 2017/11/16.
 */
@Controller
public class MonitorController {
    private static final Logger logger=Logger.getLogger(MonitorController.class);

    @Autowired
    private DSService dsService;

    @RequestMapping(path = "/monitor",method = {RequestMethod.GET,RequestMethod.POST})
    public String monitor()
    {
        return "monitor";
    }
    //http://127.0.0.1:8080/login/?username=x&&password=y

    @RequestMapping(path = "monitor/DS",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getRealDSSample(HttpServletResponse response)
    {
        try
        {
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectByIsGet(0);//得到新的样本信息
            out.print(json);//返回
        } catch (IOException e){
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    @RequestMapping(path = "monitor/DS/details",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getRealDSSampleById(@RequestParam(value = "sampleId",defaultValue = "0") String sampleId, HttpServletResponse response)
    {
        try
        {
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectBySampleId(sampleId);
            out.print(json);
        }catch (IOException e){
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }
}
