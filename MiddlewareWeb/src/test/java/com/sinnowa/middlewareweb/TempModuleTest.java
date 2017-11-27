package com.sinnowa.middlewareweb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sinnowa.middlewareweb.model.Device;
import com.sinnowa.middlewareweb.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ZingBug on 2017/11/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MiddlewarewebApplication.class)
public class TempModuleTest {
    @Test
    public void test()
    {
        Date date=new Date();
        Long cmp=date.getTime()- Utils.GetZeroDate().getTime();
        Device device=new Device();
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
        if(device!=null)
        {

        }
    }
}
