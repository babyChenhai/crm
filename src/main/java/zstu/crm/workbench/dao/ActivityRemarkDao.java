package zstu.crm.workbench.dao;

public interface ActivityRemarkDao {
    int getCountByIds(String[] ids);

    int delete(String[] ids);
}
