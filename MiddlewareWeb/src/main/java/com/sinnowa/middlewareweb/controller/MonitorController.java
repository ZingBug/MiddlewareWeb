package com.sinnowa.middlewareweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.model.DSSample;
import com.sinnowa.middlewareweb.service.DSService;
import com.sinnowa.middlewareweb.service.DeviceService;
import com.sinnowa.middlewareweb.util.JsonReader;
import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by ZingBug on 2017/11/16.
 */
@Controller
public class MonitorController {
    private static final Logger logger=Logger.getLogger(MonitorController.class);

    @Autowired
    private DSService dsService;

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(path = "/monitor",method = {RequestMethod.GET,RequestMethod.POST})
    public String monitor()
    {
        return "monitor/monitor";
    }
    //http://127.0.0.1:8080/login/?username=x&&password=y

    /**
     * 得到实时样本，获取从当前到前2个小时的样本
     * @param response
     */
    @RequestMapping(path = "monitor/DS",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getRealDSSample(HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectNewSample(8);//得到新的样本信息
            out.print(json);//返回
        } catch (IOException e){
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    @RequestMapping(path = "monitor/DS/details",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getRealDSDetails(@RequestParam(value = "sampleId",defaultValue = "0") String sampleId, HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectDetails(sampleId);
            out.print(json);
        }catch (IOException e){
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    /**
     * 得到实时仪器监控
     * @param response
     */
    @RequestMapping(path = "monitor/device",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getRealDevice(HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject json=deviceService.selectTodaySampleCount();
            out.print(json);
        }
        catch (IOException e)
        {
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    /**
     * 监控仪器
     * @return 仪器监控视图
     */
    @RequestMapping(path = "monitorDevice",method = RequestMethod.GET)
    public String monitorDevice()
    {
        return "monitor/monitorDevice";
    }
    /**
     * 监控样本
     * @return 样本监控视图
     */
    @RequestMapping(path = "monitorSample",method = RequestMethod.GET)
    public String monitorSample()
    {
        return "monitor/monitorSample";
    }
    private int intervalID=-1;
    @RequestMapping(path = "/monitor/setIntervalID",method = RequestMethod.POST)
    @ResponseBody
    public String setIntervalID(HttpServletRequest request)
    {
        try
        {
            JSONObject jsonObject=JsonReader.receivePost2Object(request);
            intervalID=jsonObject.getInteger("intervalID");
            return "OK";
        }
        catch (IOException e)
        {
            logger.error("设置定时器ID出错 "+e.getMessage());
            return "NO";
        }
    }
    @RequestMapping(path = "/monitor/getIntervalID",method = RequestMethod.GET)
    public void getIntervalID(HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("intervalID",intervalID);
            out.print(jsonObject);
        }
        catch (IOException e)
        {
            logger.error("获取定时器ID出错 "+e.getMessage());
        }
    }
}
