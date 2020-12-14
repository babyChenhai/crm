package zstu.crm.workbench.web.controller;

import zstu.crm.settings.domain.User;
import zstu.crm.settings.service.UserService;
import zstu.crm.settings.service.impl.UserServiceImpl;
import zstu.crm.utils.*;
import zstu.crm.vo.PaginationVO;
import zstu.crm.workbench.domain.Activity;
import zstu.crm.workbench.service.ActivityService;
import zstu.crm.workbench.service.impl.ActivityServiceImpl;

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
        }else if ("/workbench/activity/saveActivity.do".equals(path)){
            saveActivity(request,response);
        }else if ("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if ("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }
        else if ("/workbench/activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(request,response);
        }
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进到获取用户列表、单条活动的控制器");
        //拿到前端传来的id
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Map<String,Object> map = as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }

    //删除市场活动的方法
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的删除操作");
        String[] ids = request.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }

    //分页查询
    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到分页查询的servlet");
        //准备数据
        String pageNo = request.getParameter("pageNo");
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int skipCount = (Integer.valueOf(pageNo)-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        //创建service对象
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        /*
            前端要的数据：
             {"total":100,"dataList":[{市场活动1},{2},{3}]}
             因为常常要做分页查询，所以使用vo
         */
        PaginationVO vo =  as.pageList(map);
        PrintJson.printJsonObj(response,vo);
    }

    //获取用户列表
    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到了getUserList方法");
        //因为需要的是用户列表，所以可以调用userService，不需要用activity
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList = us.getUserList();
        PrintJson.printJsonObj(response,uList);
    }

    //保存活动
    private void saveActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到保存市场活动的控制器的方法了");

        //准备添入数据库的数据
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        Activity a = new Activity();
        a.setId(id);
        a.setName(name);
        a.setOwner(owner);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        //创建service对象处理事务
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.save(a);
        PrintJson.printJsonFlag(response,flag);
    }
}
