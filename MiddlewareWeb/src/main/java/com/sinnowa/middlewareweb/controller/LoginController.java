package com.sinnowa.middlewareweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.sinnowa.middlewareweb.dao.UserDAO;
import com.sinnowa.middlewareweb.model.User;
import com.sinnowa.middlewareweb.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ZingBug on 2017/11/13.
 */
@Controller
public class LoginController {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET,RequestMethod.POST})
    public String login()
    {
        return "login";
    }

    @RequestMapping(path = {"/login"})
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        PrintWriter out=response.getWriter();
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        System.out.println("username:"+username);
        System.out.println("password:"+password);

        JSONObject json=new JSONObject();

        //登陆
        User user=userDAO.selectByName(username);
        if(user!=null&&user.getPassword().equals(password))
        {
            json.put("result","success");
        }
        else
        {
            json.put("result","fail");
        }
        out.print(json);
    }
}
