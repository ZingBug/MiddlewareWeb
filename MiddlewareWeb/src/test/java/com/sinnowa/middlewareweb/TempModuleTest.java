package com.sinnowa.middlewareweb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sinnowa.middlewareweb.dao.DSSampleDAO;
import com.sinnowa.middlewareweb.dao.DeviceDAO;
import com.sinnowa.middlewareweb.dao.UserDAO;
import com.sinnowa.middlewareweb.model.DSSample;
import com.sinnowa.middlewareweb.model.Device;
import com.sinnowa.middlewareweb.model.down.DownConnectClient;
import com.sinnowa.middlewareweb.model.down.DownMessage;
import com.sinnowa.middlewareweb.service.DownService;
import com.sinnowa.middlewareweb.service.TCPServerService;
import com.sinnowa.middlewareweb.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DSSampleDAO dsSampleDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private TCPServerService tcpServerService;

    @Autowired
    private DownService downService;

    @Test
    public void testTcpServer()
    {
        tcpServerService.startServe();

        int num=0;
        while (true)
        {
            List<String> list=downService.getClientsIdentifier();

            for(String identifier:list)
            {
                num++;
                DownConnectClient client=downService.getClientByIdentifier(identifier);
                DownMessage downMessage=new DownMessage(identifier,client.getRemoteAddress(),"this is a message"+num);
                downService.addMessage(downMessage);
                System.out.println("已经添加新消息");
            }
            if(num>=2)
            {
                break;
            }
        }
        while (true)
        {

        }
    }
}
