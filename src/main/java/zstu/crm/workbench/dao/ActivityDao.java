package zstu.crm.workbench.dao;

import zstu.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int save(Activity a);

    List<Activity> getDataList(Map<String, Object> map);

    int getTotal(Map<String, Object> map);

    int delete(String[] ids);

    Activity getActivityById(String id);
}
