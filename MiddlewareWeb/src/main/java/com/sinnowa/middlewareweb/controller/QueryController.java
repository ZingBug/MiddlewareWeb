package com.sinnowa.middlewareweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.service.DSService;
import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.PanelUI;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by ZingBug on 2017/11/16.
 */
@Controller
public class QueryController {

    private static final Logger logger=Logger.getLogger(QueryController.class);

    @Autowired
    private DSService dsService;
    /**
     * 查询主界面
     * @return
     */
    @RequestMapping(path = "/query",method = {RequestMethod.GET,RequestMethod.POST})
    public String query()
    {
        return "query/query";
    }
    //url:"/query/"+device+"/"+"sampleByTime?"+"time"+datetime,

    @RequestMapping(path = "/query/DS/sampleByTime",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getDSSampleByTime(@RequestParam(value = "time",defaultValue = "1970-00-00 00:00:00") String time, HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start=format.parse(time);
            Date end=new Date();
            end.setTime(start.getTime()+60*60*24*1000);//增加一天时间
            JSONObject json=dsService.selectByTime(start,end);//转换json串
            out.print(json);//返回

        }catch (ParseException e)
        {
            logger.error("时间转换错误 "+e.getMessage());
        }
        catch (IOException e)
        {
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    @RequestMapping(path = "/query/DS/sampleByName",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getDSSampleByName(@RequestParam(value = "name",defaultValue = "") String name,HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectByName(name);
            out.print(json);
        }
        catch (IOException e)
        {
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    @RequestMapping(path = "/query/DS/sampleByDevice",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getDSSampleByDevice(@RequestParam(value = "device",defaultValue = "") String device,HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectByDevice(device);
            out.print(json);
        }
        catch (IOException e)
        {
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    @RequestMapping(path = "/query/DS/sampleBySampleId",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getDSSampleBySampleId(@RequestParam(value = "sampleId",defaultValue = "") String sampleId,HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectBySampleId(sampleId);
            out.print(json);
        }
        catch (IOException e)
        {
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    @RequestMapping(path = "/query/DS/details",method = RequestMethod.GET,produces = "text/json;charset=utf-8")
    public void getDSSampleDetails(@RequestParam(value = "sampleId",defaultValue = "0") String sampleId,HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectDetails(sampleId);//根据样本ID查找并转转换为json串
            out.print(json);

        }catch (IOException e)
        {
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    /**
     * 查找得到仪器列表
     * @param response
     */
    @RequestMapping(path = "/query/DS/device",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void queryDSDevice(HttpServletResponse response)
    {
        try
        {
            response.setContentType("text/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.getDevices();
            out.print(json);
        }
        catch (IOException e)
        {
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

    /**
     * 通过时间查询
     * @return 时间查询视图
     */
    @RequestMapping(path = "queryByTime",method = RequestMethod.GET)
    public String queryByTime()
    {
        return "query/queryByTime";
    }

    /**
     * 通过样本查询
     * @return 样本查询视图
     */
    @RequestMapping(path = "queryBySample",method = RequestMethod.GET)
    public String queryBySample()
    {
        return "query/queryBySample";
    }

    /**
     * 通过具体仪器查询
     * @return 仪器查询视图
     */
    @RequestMapping(path = "queryByDevice",method = RequestMethod.GET)
    public String queryByDevice()
    {
        return "query/queryByDevice";
    }

    /**
     * 通过病人姓名查询
     * @return 病人查询视图
     */
    @RequestMapping(path = "queryByName",method = RequestMethod.GET)
    public String queryByName()
    {
        return "query/queryByName";
    }
}
