package priv.anna.reggie.service.Impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.anna.reggie.domain.User;
import priv.anna.reggie.mapper.UserMapper;
import priv.anna.reggie.service.UserService;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 移动端用户登录
     *
     * @param map
     * @param session
     */
    @Override
    public User login(Map map, HttpSession session) {

        User user =new User();

        //获取手机号,获取用户提交的验证码
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        //获取session中的验证码
        Object codeInSession = session.getAttribute(phone);

        //进行验证码的比对
        if (codeInSession != null && codeInSession.equals(code)){
            //比对成功，能够登录
            user.setPhone(phone);


            //判断当前手机号是否在用户表中，若不在（新用户）则保存
            User userByPhone = userMapper.findUserByPhone(phone);
            if (userByPhone==null){
                user.setPhone(phone);
                user.setStatus(1);
                userMapper.save(user);

                User user1 = userMapper.findUserByPhone(phone);
                session.setAttribute("user",user1.getId());
            }
            else {

                session.setAttribute("user",userByPhone.getId());
            }

        }
        return user;
    }
}
