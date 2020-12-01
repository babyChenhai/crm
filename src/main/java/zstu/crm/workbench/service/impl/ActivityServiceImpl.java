package zstu.crm.workbench.service.impl;

import zstu.crm.utils.ServiceFactory;
import zstu.crm.utils.SqlSessionUtil;
import zstu.crm.workbench.dao.ActivityDao;
import zstu.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
}
