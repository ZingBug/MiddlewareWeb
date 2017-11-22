package com.sinnowa.middlewareweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ZingBug on 2017/11/14.
 */
@Controller
public class TestController {

    @RequestMapping(path = {"test"},method = {RequestMethod.GET,RequestMethod.POST})
    public String test(HttpServletRequest request, HttpServletResponse response)
    {
        return "test";
    }

    @RequestMapping(path = {"table-time"},method = {RequestMethod.GET,RequestMethod.POST})
    public String test1(HttpServletRequest request, HttpServletResponse response)
    {
        return "query/table-time";
    }

}
