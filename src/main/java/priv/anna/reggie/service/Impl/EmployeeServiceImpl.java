package priv.anna.reggie.service.Impl;

//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import priv.anna.reggie.domain.Employee;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.mapper.EmployeeMapper;
import priv.anna.reggie.service.EmployeeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     *员工登录
     */
    @Override
    public Employee login(Employee employee) {
        //1、将页面提交的密码 passWord 进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2、根据页面提交的用户名 username 查询数据库
        String username = employee.getUsername();
        Employee emp = employeeMapper.findByUsername(username);

        return emp;
    }

    /**
     * 新增员工
     */
    @Override
    public boolean save(Employee employee,long empId) {

    //交给全局异常处理器
        //查询用户名是否已存在
//        String username = employee.getUsername();
//        Employee byUsername = employeeMapper.findByUsername(username);
//        if (byUsername != null){
//            return false;
//        }

        //给员工设置初始密码123456，进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //设置创建时间
        employee.setCreateTime(LocalDateTime.now());
        //设置更新时间
        employee.setUpdateTime(LocalDateTime.now());

        //设置员工的创建者
        employee.setCreateUser(empId);
        //设置员工的更新者
        employee.setUpdateUser(empId);

        //将员工信息存入数据库
        boolean result = employeeMapper.saveEmployee(employee);

        return result;
    }

    /**
     * 员工信息分页查询
     * @param page    当前页
     * @param pageSize 每页条数
     * @param name    查询条件
     * @return
     */
    @Override
    public Page page(int page, int pageSize, String name) {
        //开启分页
        PageHelper.startPage(page,pageSize); //传入当前页、每页的数量

        //查询结果放入pageInfo
        PageInfo<Employee> pageInfo;

        //如果name不为空，按name查询
        if (name != null){
            List<Employee> listbyName = employeeMapper.findByName(name);
            pageInfo=new PageInfo<>(listbyName);
        }
        else {  //name为空，查找所有
            List<Employee> all = employeeMapper.findAll();
            pageInfo=new PageInfo<>(all);
        }

        //这里的list实际上是一个Page对象，所以还是把employee遍历出来再另外封装比较好
        List<Employee> list = pageInfo.getList();
        List<Employee> pageEmployee = new ArrayList<Employee>(list);  //相当于把list的employee遍历出来再封装进pageEmployee

        //要返回的结果，包装成自己写的page类
        Page<Employee> page1 = new Page<Employee>();
        page1.setTotal(pageInfo.getTotal());
        page1.setRecords(pageEmployee);

        return page1;
    }


    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    @Override
    public boolean updateById(Employee employee,Long empId) {
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        return employeeMapper.updateById(employee);
    }


    /**
     * 根据id查找员工信息
     * @param id
     * @return
     */
    @Override
    public Employee findById(long id) {
        return employeeMapper.findById(id);
    }
}
