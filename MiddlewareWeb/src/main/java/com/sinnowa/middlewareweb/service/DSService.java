package com.sinnowa.middlewareweb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.dao.DSSampleDAO;
import com.sinnowa.middlewareweb.model.DSSample;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by ZingBug on 2017/11/16.
 */
@Service
public class DSService {
    private static final Logger logger=Logger.getLogger(DSService.class);

    @Autowired
    private DSSampleDAO dsSampleDAO;

    /**
     * 根据时间段查询数据库，并返回json，根据样本ID选择，而不是根据ITEM，所以每个ID只能有一条数据
     * @param start 开始时间
     * @param end 结束时间
     * @return json
     */
    public JSONObject selectByTime(Date start,Date end)
    {
        JSONObject json=new JSONObject();
        List<DSSample> list=dsSampleDAO.selectByTime(start,end);
        for(DSSample dsSample:list)
        {
            if(!json.containsKey(dsSample.getSampleId()))
            {
                json.put(dsSample.getSampleId(),dsSample);
            }
        }
        return json;
    }

    /**
     * 根据样本ID查询数据库，只会返回一条数据
     * @param sampleId 样本ID
     * @return json
     */
    public JSONObject selectBySampleId(String sampleId)
    {
        JSONObject json=new JSONObject();
        List<DSSample> list=dsSampleDAO.selectBySampleId(sampleId);
        for(DSSample dsSample:list)
        {
            if(!json.containsKey(dsSample.getSampleId()))
            {
                json.put(dsSample.getSampleId(),dsSample);
            }
        }
        return json;
    }

    /**
     * 根据样本ID查询数据库，并返回json，一个样本ID内含有多条ITEM数据。
     * @param sampleId
     * @return
     */
    public JSONObject selectDetails(String sampleId)
    {
        JSONObject json=new JSONObject();
        List<DSSample> list=dsSampleDAO.selectBySampleId(sampleId);
        for(DSSample dsSample:list)
        {
            json.put(dsSample.getItem(),dsSample);
        }
        return json;
    }

    /**
     * 查找新的样本信息
     * @param isGet 0代表新的样本
     * @return json
     */
    public JSONObject selectByIsGet(int isGet)
    {
        JSONObject json=new JSONObject();
        //查找数据库中新的样本
        List<DSSample> list=dsSampleDAO.selectByIsGet(isGet);
        for(DSSample dsSample:list)
        {
            if(!json.containsKey(dsSample.getSampleId()))
            {
                json.put(dsSample.getSampleId(),dsSample);
            }
            updateSampleIsGet(dsSample);
        }
        return json;
    }

    /**
     * 根据病人姓名来查询样本
     * @param name 病人姓名
     * @return json
     */
    public JSONObject selectByName(String name)
    {
        JSONObject json=new JSONObject();
        List<DSSample> list=dsSampleDAO.selectByName(name);
        for(DSSample dsSample:list)
        {
            if(!json.containsKey(dsSample.getSampleId()))
            {
                json.put(dsSample.getSampleId(),dsSample);
            }
        }
        return json;
    }

    /**
     * 根据检验仪器来查询样本
     * @param device 检验仪器
     * @return json
     */
    public JSONObject selectByDevice(String device)
    {
        JSONObject json=new JSONObject();
        List<DSSample> list=dsSampleDAO.selectByDevice(device);
        for(DSSample dsSample:list)
        {
            if(!json.containsKey(dsSample.getSampleId()))
            {
                json.put(dsSample.getSampleId(),dsSample);
            }
        }
        return json;
    }

    /**
     * 根据日期查询最新的多条数据
     * @param limit 数量限制
     * @return json
     */
    public JSONObject selectNewSample(int limit)
    {
        JSONObject json=new JSONObject();
        List<DSSample> list=dsSampleDAO.selectNewSameple(limit);
        for(DSSample dsSample:list)
        {
            if(!json.containsKey(dsSample.getSampleId()))
            {
                json.put(dsSample.getSampleId(),dsSample);
            }
        }
        return json;
    }

    /**
     * 返回生化数据库中的仪器型号列表
     * @return json
     */
    public JSONObject getDevices()
    {
        JSONObject json=new JSONObject();
        List<String> list=dsSampleDAO.getDevices();
        for(String device:list)
        {
            if(!json.containsKey(device))
            {
                json.put(device,"");
            }
        }
        return json;
    }

    /**
     * 往数据库添加新的样本信息
     * @param sample 新的样本
     */
    public void addSample(DSSample sample)
    {
        List<DSSample> list=dsSampleDAO.selectBySampleId(sample.getSampleId());
        for(DSSample dsSample:list)
        {
            if(sample.getItem().equals(dsSample.getItem()))
            {
                //如果之前有此项目，则进行更新结果
                dsSampleDAO.updateResult(sample);
                return;
            }
        }
        //如果之前没有此项目，则添加结果
        sample.setIsGet(0);//设置为0
        dsSampleDAO.addDSSample(sample);
    }

    /**
     * 更新样本IsGet字段，当样本显示后，将该字段设置为1
     * @param sample
     */
    public void updateSampleIsGet(DSSample sample)
    {
        sample.setIsGet(1);//设置为1
        dsSampleDAO.updateIsGet(sample);
    }

}
