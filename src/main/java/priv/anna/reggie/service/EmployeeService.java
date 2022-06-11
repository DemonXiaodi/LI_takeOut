package priv.anna.reggie.service;

//import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;
import priv.anna.reggie.common.R;
import priv.anna.reggie.domain.Employee;
import priv.anna.reggie.domain.Page;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService {

    /**
     *员工登录
     * @return
     */
    public Employee login(Employee employee);

    /**
     * 新增员工
     */
    public boolean save(Employee employee,long empId);

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    public Page page(int page, int pageSize, String name);

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    boolean updateById(Employee employee,Long empId);

    /**
     * 根据id查找员工信息
     * @param id
     * @return
     */
    Employee findById(long id);

}
