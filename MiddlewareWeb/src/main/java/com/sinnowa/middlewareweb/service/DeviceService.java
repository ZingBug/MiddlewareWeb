package com.sinnowa.middlewareweb.service;


import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.dao.DeviceDAO;
import com.sinnowa.middlewareweb.model.DSSample;
import com.sinnowa.middlewareweb.model.Device;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sinnowa.middlewareweb.util.Utils;

import java.util.Date;
import java.util.List;

import static com.sinnowa.middlewareweb.util.Utils.DateToShortFormate;

/**
 * Created by ZingBug on 2017/11/26.
 */
@Service
public class DeviceService {
    private static final Logger logger=Logger.getLogger(DeviceService.class);

    @Autowired
    private DeviceDAO deviceDAO;

    /**
     * 得到各个仪器样本数目
     * @return
     */
    public JSONObject selectTodaySampleCount()
    {
        JSONObject json=new JSONObject();
        List<Device> list=deviceDAO.selectTodayDevice();
        for(Device device:list)
        {
            if(!json.containsKey(device.getDevice()))
            {
                json.put(device.getDevice(),device);
            }
        }
        return json;
    }

    /**
     * 根据样本数据来更新仪器数据
     * @param dsSample 样本数据
     */
    public void updateResult(DSSample dsSample)
    {
        String deviceName=dsSample.getDevice();
        Date date=dsSample.getTime();

        //先查找是否存在此仪器的历史记录
        Device device=deviceDAO.selectByDeviceAndDate(deviceName,DateToShortFormate(date));
        if(device==null)
        {
            //不存在历史记录，则直接添加新纪录
            device=new Device(deviceName,date);
            updateResult(device);
            deviceDAO.addDevice(device);
        }
        else
        {
            //存在历史数据，需要更新记录
            device.setTime(date);
            updateResult(device);
            deviceDAO.updateResultByDeviceAndDate(device);
        }
    }

    /**
     * 更新device类(相应时间点+1)，辅助上一个函数
     * @param device 仪器数据
     */
    private void updateResult(Device device)
    {
        if(device==null)
        {
            throw new NullPointerException();
        }
        Date date=device.getTime();
        Long cmp=date.getTime()-Utils.GetZeroDate(date).getTime();
        switch ((int)(cmp/(3600*1000)))
        {
            case 0:
            case 1:{
                device.setSampleCount0(device.getSampleCount0()+1);
                break;
            }
            case 2:
            case 3:{
                device.setSampleCount2(device.getSampleCount2()+1);
                break;
            }
            case 4:
            case 5:{
                device.setSampleCount4(device.getSampleCount4()+1);
                break;
            }
            case 6:
            case 7:{
                device.setSampleCount6(device.getSampleCount6()+1);
                break;
            }
            case 8:
            case 9:{
                device.setSampleCount8(device.getSampleCount8()+1);
                break;
            }
            case 10:
            case 11:{
                device.setSampleCount10(device.getSampleCount10()+1);
                break;
            }
            case 12:
            case 13:{
                device.setSampleCount12(device.getSampleCount12()+1);
                break;
            }
            case 14:
            case 15:{
                device.setSampleCount14(device.getSampleCount14()+1);
                break;
            }
            case 16:
            case 17:{
                device.setSampleCount16(device.getSampleCount16()+1);
                break;
            }
            case 18:
            case 19:{
                device.setSampleCount18(device.getSampleCount18()+1);
                break;
            }
            case 20:
            case 21:{
                device.setSampleCount20(device.getSampleCount20()+1);
                break;
            }
            case 22:
            case 23:{
                device.setSampleCount22(device.getSampleCount22()+1);
                break;
            }
            default:break;
        }
    }
}
