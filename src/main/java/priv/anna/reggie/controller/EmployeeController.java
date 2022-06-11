package priv.anna.reggie.controller;

//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import priv.anna.reggie.common.R;
import priv.anna.reggie.domain.Employee;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 员工管理
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee  页面提交的数据，包括账号密码
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //1、将页面提交的密码 passWord 进行md5加密处理
        // 2、根据页面提交的用户名 username 查询数据库
        Employee emp = employeeService.login(employee);

        //3、如果没有查询到则返回登录失败结果
        if (emp == null){
            return R.error("登录失败，账号不存在");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(emp.getPassword())){
            return R.error("登录失败，密码错误");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0){
            return R.error("登录失败，账号已禁用");
        }

        //6、登录成功，将员工 id 存入 Session 并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }

    /**
     * 员工退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());

        //获得当前登录用户的id
        long empId = (long)request.getSession().getAttribute("employee");

        boolean result = employeeService.save(employee, empId);

        return R.success("新增新员工成功");
    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){  //注意这里的泛型Page类是 priv.anna.reggie.domain.Page
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);

        Page fenyePage = employeeService.page(page, pageSize, name);

        return R.success(fenyePage);
    }

    /**
     * 启用/禁用员工，编辑员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        //获取操作修改的人的id
        Long empId = (Long)request.getSession().getAttribute("employee");
        employeeService.updateById(employee,empId);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息，实现数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息");
        Employee employee = employeeService.findById(id);
        if (employee == null){
            return R.error("没有查询到对应员工信息");
        }
        return R.success(employee);
    }


}
