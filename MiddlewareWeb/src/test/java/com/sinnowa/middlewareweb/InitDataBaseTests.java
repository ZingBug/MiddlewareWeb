package com.sinnowa.middlewareweb;

import com.sinnowa.middlewareweb.dao.DSSampleDAO;
import com.sinnowa.middlewareweb.dao.UserDAO;
import com.sinnowa.middlewareweb.model.DSSample;
import com.sinnowa.middlewareweb.model.User;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by ZingBug on 2017/11/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MiddlewarewebApplication.class)
@Sql("/init-schema.sql")
public class InitDataBaseTests {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DSSampleDAO dsSampleDAO;

    @Test
    public void initData()
    {
        User user=new User();
        user.setId(5);
        user.setName("lzh");
        user.setPassword("72f865de029e107b03ba476db319751f");
        userDAO.addUser(user);

        for(int i=0;i<10;i++)
        {
            DSSample dsSample=new DSSample();
            dsSample.setId(i);
            dsSample.setSampleId(String.format("SAMPLE%d",i));
            dsSample.setPatientId(String.format("PATIENT%d",i));
            dsSample.setItem("ITEM");
            dsSample.setType("TYPE");
            dsSample.setSendTime(new Date());
            dsSample.setDevice("DEVICE");
            dsSample.setFullName(String.format("NAME%d",i));
            dsSample.setResult(i);
            dsSample.setUnit("UNIT");
            dsSample.setNormalLow(0);
            dsSample.setNormalHigh(100);
            dsSample.setTime(new Date());
            dsSample.setIndicate("INDICATE");
            dsSample.setFirstName(String.format("FIRSTNAME%d",i));
            dsSample.setSex("SEX");
            dsSample.setAge("AGE");
            dsSample.setSampleKind("KIND");
            dsSample.setDoctor("DOCTOR");
            dsSample.setArea("AREA");
            dsSample.setAge("AGE");
            dsSample.setBed(String.format("BED%d",i));
            dsSample.setDepartment("DEPARTMENT");
            dsSample.setIsGet(0);
            dsSampleDAO.addDSSample(dsSample);
        }
        /*测试DAO用例*/
        /*

        dsSampleDAO.deleteBySampleId("SAMPLE0");

        dsSampleDAO.deleteById(2);

        DSSample dsSample=new DSSample();
        dsSample.setId(1);
        dsSample.setSampleId(String.format("SAMPLE%d",8));
        dsSample.setPatientId(String.format("PATIENT%d",8));
        dsSample.setItem("ITEM");
        dsSample.setType("TYPE");
        dsSample.setSendTime(new Date());
        dsSample.setDevice("DEVICE");
        dsSample.setFullName(String.format("NAME%d",8));
        dsSample.setResult(66.60);
        dsSample.setUnit("UNIT");
        dsSample.setNormalLow(0);
        dsSample.setNormalHigh(100);
        dsSample.setTime(new Date());
        dsSample.setIndicate("INDICATE");
        dsSample.setFirstName(String.format("FIRSTNAME%d",8));
        dsSample.setSex("SEX");
        dsSample.setAge("AGE");
        dsSample.setSampleKind("KIND");
        dsSample.setDoctor("DOCTOR");
        dsSample.setArea("AREA");
        dsSample.setAge("AGE");
        dsSample.setBed(String.format("BED%d",1));
        dsSample.setDepartment("DEPARTMENT");
        dsSample.setIsGet(0);

        dsSampleDAO.updateResult(dsSample);

        Date before=new Date();
        long beforetime=(before.getTime()/1000)-60*60*24;
        long aftertime=(before.getTime()/1000)+10;
        before.setTime(beforetime*1000);

        Date after=new Date();
        after.setTime(aftertime*1000);

        dsSampleDAO.deleteByTime(before,after);

        List<DSSample> dsSamples;//=dsSampleDAO.selectByTime(format.format(before),format.format(after));
        //dsSamples=dsSampleDAO.selectByTime("2017-11-14 21:46:51","2017-11-15 21:46:51");
        dsSamples=dsSampleDAO.selectByTime(before,after);

        for(DSSample temp:dsSamples)
        {
            System.out.println(temp.getFullName());
        }
        */

    }
}
