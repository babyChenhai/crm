package zstu.crm.settings.web.controller;

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
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入UserServlet了");
        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path)){
            //接收账号、密码,获取ip
            String loginAct = request.getParameter("loginAct");
            String loginPwd = request.getParameter("loginPwd");
            loginPwd = MD5Util.getMD5(loginPwd);
            String ip = request.getRemoteAddr();
            UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
            //调用服务层处理登陆业务
            try {
                //成功
                User user = us.login(loginAct,loginPwd,ip);
                request.getSession().setAttribute("user",user);
                PrintJson.printJsonFlag(response,true);
            }catch (Exception e){
                e.printStackTrace();
                String msg = e.getMessage();
                Map<String,Object> map = new HashMap<>();
                map.put("success",false);
                map.put("msg",msg);
                PrintJson.printJsonObj(response,map);
            }
        }
    }
}
