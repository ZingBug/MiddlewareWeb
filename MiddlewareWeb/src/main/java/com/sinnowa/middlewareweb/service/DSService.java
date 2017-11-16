package com.sinnowa.middlewareweb.service;

import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.dao.DSSampleDAO;
import com.sinnowa.middlewareweb.model.DSSample;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param start
     * @param end
     * @return json
     */
    public JSONObject selectByTime(Date start,Date end)
    {
        JSONObject json=new JSONObject();
        List<DSSample> list=dsSampleDAO.selectByTime(start,end);
        Hashtable<String,DSSample> hashtable=new Hashtable<>();
        for(DSSample dsSample:list)
        {
            if(!hashtable.containsKey(dsSample.getSampleId()))
            {
                hashtable.put(dsSample.getSampleId(),dsSample);
                json.put(dsSample.getSampleId(),dsSample);
            }
        }
        return json;
    }
}
