package com.sinnowa.middlewareweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.service.DSService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        return "query";
    }
    //url:"/query/"+device+"/"+"sampleByTime?"+"time"+datetime,

    @RequestMapping(path = "/query/DS/sampleByTime",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getDSSampleByTime(@RequestParam(value = "time",defaultValue = "1970-00-00 00:00:00") String time, HttpServletResponse response)
    {
        try
        {
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

    @RequestMapping(path = "/query/DS/details",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getDSSampleById(@RequestParam(value = "sampleId",defaultValue = "0") String sampleId,HttpServletResponse response)
    {
        try
        {
            PrintWriter out=response.getWriter();
            JSONObject json=dsService.selectBySampleId(sampleId);//根据样本ID查找并转转换为json串
            out.print(json);

        }catch (IOException e)
        {
            logger.error("读取http响应出错 "+e.getMessage());
        }
    }

}
