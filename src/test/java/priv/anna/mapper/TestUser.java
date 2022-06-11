package priv.anna.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.anna.reggie.ReggieApplication;
import priv.anna.reggie.domain.AddressBook;
import priv.anna.reggie.mapper.UserMapper;
import priv.anna.reggie.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ReggieApplication.class)
public class TestUser {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    public void test1(){

//        HashMap<String,String> map = new HashMap<>();
//        map.put("phone","13455456765");
//        map.put("code","4354");
//
//      HttpSession session = null;
//
//        userService.login(map,session);
    }


}
