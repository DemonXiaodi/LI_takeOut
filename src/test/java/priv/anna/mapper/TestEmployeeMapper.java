package priv.anna.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import priv.anna.reggie.ReggieApplication;
import priv.anna.reggie.domain.Employee;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.mapper.EmployeeMapper;
import priv.anna.reggie.service.EmployeeService;


import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest(classes = ReggieApplication.class)
public class TestEmployeeMapper {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void test1(){
        List<Employee> all = employeeMapper.findAll();
        Employee admin = employeeMapper.findByUsername("admin");
        System.out.println(all);
    }

    @Test
    public void test2(){
        Employee admin = employeeMapper.findByUsername("admin");
        System.out.println(admin);
    }

    @Test
    public void test3(){
        Employee employee = new Employee();
        long id = 13424;
        employee.setId(id);
        employee.setName("Guanli");
        employee.setUsername("admin3");
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setPhone("12321313");
        employee.setSex("1");
        employee.setIdNumber("123456789098765432");
        employee.setStatus(1);
        //设置创建时间
        employee.setCreateTime(LocalDateTime.now());
        //设置更新时间
        employee.setUpdateTime(LocalDateTime.now());
        long id1 = 1;
        employee.setCreateUser(id1);
        employee.setUpdateUser(id1);

        boolean b = employeeMapper.saveEmployee(employee);
        System.out.println(b);
    }

    @Test
    public void test4(){
        Employee employee = new Employee();
        long id = 2;
        employee.setId(null);
        employee.setName("Guanli");
        employee.setUsername("admin14");
//        employee.setPassword("123456");
        employee.setPhone("12321313");
        employee.setSex("1");
        employee.setIdNumber("123456789098765333");
        employee.setStatus(null);

        long id1 = 1;
        boolean save = employeeService.save(employee, id1);
        System.out.println(save);
    }

    @Test
    public void test5(){
        List<Employee> guanli = employeeMapper.findByName("Guanli");
        System.out.println(guanli);
    }

    @Test
    public void test6(){
        Page page = employeeService.page(1, 2, "G");
        System.out.println(page);
    }

    @Test
    public void test7(){
        Employee employee = new Employee();
        long id = 18;
        employee.setId(id);
        employee.setName("Guanli");
        employee.setUsername("aaa18");
//        employee.setPassword("123456");
        employee.setPhone("12388888888");
        employee.setSex("1");
        employee.setIdNumber("123456789098765333");
        employee.setStatus(0);
        employee.setUpdateTime(LocalDateTime.now());
        long id1 = 3;
        employee.setUpdateUser(id1);

        boolean b = employeeMapper.updateById(employee);
        System.out.println(b);
    }

    @Test
    public void test8(){
        long id =1;
        Employee byId = employeeMapper.findById(id);
        System.out.println(byId);
    }





}
