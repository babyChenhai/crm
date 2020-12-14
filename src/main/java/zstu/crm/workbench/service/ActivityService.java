package zstu.crm.workbench.service;

import zstu.crm.vo.PaginationVO;
import zstu.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {

    boolean save(Activity a);

    PaginationVO pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);
}
