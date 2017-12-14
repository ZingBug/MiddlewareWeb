package com.sinnowa.middlewareweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.model.down.DownConnectClient;
import com.sinnowa.middlewareweb.model.down.DownMessage;
import com.sinnowa.middlewareweb.model.down.DownSampleInfo;
import com.sinnowa.middlewareweb.model.down.DownSampleTaskInfo;
import com.sinnowa.middlewareweb.service.DownService;
import com.sinnowa.middlewareweb.util.JsonReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by ZingBug on 2017/11/28.
 */
@Controller
public class ManageController {
    private static final Logger logger=Logger.getLogger(ManageController.class);

    @Autowired
    private DownService downService;

    @RequestMapping(path = "/manage",method = {RequestMethod.GET,RequestMethod.POST})
    public String manage()
    {
        return "manage/manage";
    }

    /**
     * 下发样本
     * @param request 请求
     * @param response 响应
     */
    @RequestMapping(path = "manage/download/downData",method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    public void test(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            PrintWriter out=response.getWriter();

            /*生成下载样本信息*/
            JSONObject json=JsonReader.receivePost2Object(request);
            DownSampleInfo downSample= json.toJavaObject(DownSampleInfo.class);
            List<DownSampleTaskInfo> items=new LinkedList<>();

            JSONObject itemIndexs=(JSONObject) json.get("itemIndex");
            Set<String> keys=itemIndexs.keySet();
            for(String key:keys)
            {
                int index=Integer.valueOf((String)itemIndexs.get(key));
                items.add(new DownSampleTaskInfo(downSample.getSampleId(),index,String.valueOf(index),downSample.getKind(),downSample.getDevice()));
            }
            downSample.setItems(items);//生成一个下载样本数据

            /*写入数据库中*/
            downService.addDownMessageToDB(downSample);

            /*生成下载消息*/
            DownConnectClient client=downService.getClientByIdentifier(downSample.getDevice());
            DownMessage downMessage=new DownMessage(client.getIdentifier(),client.getRemoteAddress(),downSample.getHl7Message());

            //将下载消息添加到下载队列中
            downService.addMessage(downMessage);

            out.print("ok");
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前连接仪器
     * @param response
     */
    @RequestMapping(path = "manage/download/getDevice",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    public void getDevice(HttpServletResponse response)
    {
        try
        {
            PrintWriter out=response.getWriter();
            JSONObject json=new JSONObject();
            List<String> devices=downService.getClientsIdentifier();
            for(String device:devices)
            {
                json.put(device,device);
            }
            out.print(json);
        }catch (IOException e)
        {
            logger.error(e.getMessage());
        }
    }

    /**
     * 下发样本
     * @return 下发样本视图
     */
    @RequestMapping(path = "download",method = RequestMethod.GET)
    public String download()
    {
        return "manage/download";
    }
}
