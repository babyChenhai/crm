package zstu.crm.settings.service;

import zstu.crm.exceptions.LoginException;
import zstu.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
