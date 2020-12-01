package zstu.crm.workbench.web.controller;

import zstu.crm.settings.domain.User;
import zstu.crm.settings.service.UserService;
import zstu.crm.settings.service.impl.UserServiceImpl;
import zstu.crm.utils.MD5Util;
import zstu.crm.utils.PrintJson;
import zstu.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ActivityServlet");
        String path = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到了getUserList方法");
        //因为需要的是用户列表，所以可以调用userService，不需要用activity
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response,uList);
    }
}
