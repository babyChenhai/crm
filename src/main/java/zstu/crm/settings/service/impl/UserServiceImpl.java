package zstu.crm.settings.service.impl;

import zstu.crm.exceptions.LoginException;
import zstu.crm.settings.dao.UserDao;
import zstu.crm.settings.domain.User;
import zstu.crm.settings.service.UserService;
import zstu.crm.utils.DateTimeUtil;
import zstu.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        System.out.println("进入服务层了");
        //将账号、密码传给dao层，dao层查询数据库
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userDao.login(map);
        if (user == null)
            throw new LoginException("账号或密码不正确，请重新输入");

        //判断锁定状态、时限、ip
        String lockState = user.getLockState();
        if ("0".equals(lockState))
            throw new LoginException("账号已被锁定");

        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime)<0)
            throw new LoginException("账号已失效");

        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip))
            throw new LoginException("ip地址受限");
        return user;
    }

    @Override
    public List<User> getUserList() {
        System.out.println("进入到了实现层的dao方法");
        List<User> uList = userDao.getUserList();
        return uList;
    }
}
