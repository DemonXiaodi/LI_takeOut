package priv.anna.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.anna.reggie.common.R;
import priv.anna.reggie.domain.User;
import priv.anna.reggie.service.UserService;
import priv.anna.reggie.utils.SMSUtils;
import priv.anna.reggie.utils.ValidateCodeUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMessage(@RequestBody User user, HttpSession session){

        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);

            //调用阿里云短信服务发送短信
            //SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);

            //需要将生成的验证码保存到session中
            session.setAttribute(phone,code);
            return R.success("手机验证码发送短信成功");
        }
        return R.error("发送失败");
    }


    /**
     * 移动端用户登录
     * @param
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> Login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());
        User user = userService.login(map, session);
        if (user.getPhone() != null){
            return R.success(user);
        }

        return R.error("登录失败");

    }



}
