package zstu.crm.workbench.service.impl;

import zstu.crm.settings.dao.UserDao;
import zstu.crm.settings.domain.User;
import zstu.crm.utils.ServiceFactory;
import zstu.crm.utils.SqlSessionUtil;
import zstu.crm.vo.PaginationVO;
import zstu.crm.workbench.dao.ActivityDao;
import zstu.crm.workbench.dao.ActivityRemarkDao;
import zstu.crm.workbench.domain.Activity;
import zstu.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity a) {
        boolean flag = true;
        Integer count = activityDao.save(a);
        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVO pageList(Map<String, Object> map) {
        //取得total
        int total = activityDao.getTotal(map);
        //取得dataList
        List<Activity> dataList = activityDao.getDataList(map);
        //包装为vo
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;

        //先删除市场活动的备注
        //1.1查询出要删除的备注的数量
        int count1 = activityRemarkDao.getCountByIds(ids);
        System.out.println("备注数量"+count1);
        //1.2删除之
        int count2 = activityRemarkDao.delete(ids);

        if (count1 != count2){
            flag = false;
        }

        //再删除市场活动本身
        int count3 = activityDao.delete(ids);
        if (count3 != ids.length){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> uList = userDao.getUserList();
        Activity a = activityDao.getActivityById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag = true;
        Integer count = activityDao.update(a);
        if (count != 1){
            flag = false;
        }
        return flag;
    }
}
