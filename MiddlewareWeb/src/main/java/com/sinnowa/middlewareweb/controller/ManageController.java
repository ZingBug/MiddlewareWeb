package com.sinnowa.middlewareweb.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ZingBug on 2017/11/28.
 */
@Controller
public class ManageController {
    private static final Logger logger=Logger.getLogger(ManageController.class);

    @RequestMapping(path = "/manage",method = {RequestMethod.GET,RequestMethod.POST})
    public String manage()
    {
        return "manage/manage";
    }
}
