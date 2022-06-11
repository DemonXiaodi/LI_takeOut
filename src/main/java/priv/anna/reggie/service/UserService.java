package priv.anna.reggie.service;

import priv.anna.reggie.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {

    /**
     * 移动端用户登录
     */
    User login(Map map, HttpSession session);

}
