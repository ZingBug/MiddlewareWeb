package com.sinnowa.middlewareweb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sinnowa.middlewareweb.dao.DSSampleDAO;
import com.sinnowa.middlewareweb.dao.DeviceDAO;
import com.sinnowa.middlewareweb.dao.UserDAO;
import com.sinnowa.middlewareweb.model.DSSample;
import com.sinnowa.middlewareweb.model.Device;
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

    @Test
    public void test()
    {
        List<DSSample> list=dsSampleDAO.selectNewSameple(8);
        if(list.isEmpty())
        {

        }
    }
}
