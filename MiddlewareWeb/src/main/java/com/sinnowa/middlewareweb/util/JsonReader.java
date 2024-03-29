package com.sinnowa.middlewareweb.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ZingBug on 2017/11/18.
 */
public class JsonReader {
    private static final Logger logger= Logger.getLogger(JsonReader.class);

    public static String receivePost2String(HttpServletRequest request) throws IOException
    {
        BufferedReader in=new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
        String line=null;
        StringBuilder sb=new StringBuilder();
        while ((line=in.readLine())!=null)
        {
            sb.append(line);
        }
        return sb.toString();
    }

    public static JSONObject receivePost2Object(HttpServletRequest request) throws IOException
    {
        String json=receivePost2String(request);
        return JSONObject.parseObject(json);
    }
}
