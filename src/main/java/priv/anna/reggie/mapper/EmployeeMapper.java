package priv.anna.reggie.mapper;

//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.Employee;

import java.util.List;

@Mapper
public interface EmployeeMapper{

    //查询
    public List<Employee> findAll();

    /**
     * 通过用户名查询员工信息
     * @param username
     * @return
     */
    Employee findByUsername(String username);

    /**
     * 将员工信息存入数据库
     */
    boolean saveEmployee(Employee employee);

    /**
     * 按姓名查询，模糊查询
     */
    List<Employee> findByName(String name);

    /**
     * 通过 id 更新员工信息
     */
    boolean updateById(Employee employee);

    /**
     * 通过 id 查询员工信息
     */
    Employee findById(long  Id);



}
