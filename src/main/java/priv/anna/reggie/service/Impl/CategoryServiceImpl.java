package priv.anna.reggie.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.anna.reggie.common.CustomException;
import priv.anna.reggie.domain.Category;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.mapper.CategoryMapper;
import priv.anna.reggie.mapper.DishMapper;
import priv.anna.reggie.service.CategoryService;
import priv.anna.reggie.service.DishService;
import priv.anna.reggie.service.SetmealService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增菜品、套餐分类
     * @param category
     * @param empId
     * @return
     */
    @Override
    public Boolean save(Category category,Long empId) {

    //目前还没用公共字段自动填充改进代码，只能先这样
        category.setCreateTime(LocalDateTime.now()); //设置创建时间
        category.setUpdateTime(LocalDateTime.now()); //设置更新时间
        category.setCreateUser(empId);//设置员工的创建者
        category.setUpdateUser(empId); //设置员工的更新者

        //name字段有唯一属性，展示是否同名交给全局异常处理器

        return  categoryMapper.save(category);
    }

    /**
     * 菜品分类信息的分页，按sort排序
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Category> page(int page, int pageSize) {
        //开启分页
        PageHelper.startPage(page,pageSize);//传入当前页、每页的数量

        //设定排序规律，按sort字段排序，升序
        PageHelper.orderBy("sort asc");

        //查询
        List<Category> all = categoryMapper.findAll();
        PageInfo<Category> pageInfo= new PageInfo<Category>(all);

        //把真正的List<Category>遍历出来再另外封装
        List<Category> list = pageInfo.getList();
        List<Category> pageEmployee = new ArrayList<Category>(list);  //相当于把list的employee遍历出来再封装进pageEmployee

        //要返回的结果，包装成自己写的page类
        Page<Category> page1 = new Page<Category>();
        page1.setTotal(pageInfo.getTotal());
        page1.setRecords(pageEmployee);

        return page1;
    }

    /**
     * 分类信息的修改
     * @param category
     * @param empId
     * @return
     */
    @Override
    public Boolean update(Category category, Long empId) {
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(empId);

        return categoryMapper.update(category);
    }

    /**
     * 根据id删除分类
     * 删除之前判断当前分类是否关联了菜品套餐
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {

        //查询当前分类是否关联了菜品，如果已经关联了，抛出一个业务异常
        int count = dishService.findCountByCategoryId(id);
        if (count > 0){  //已关联
            throw  new CustomException("当前分类下关联了菜品，不能删除");

        }

        //查询当前分类是否关联了套餐，如果已经关联了，抛出一个业务异常
        int count2 = setmealService.findCountByCategoryId(id);
        if (count2 > 0){   //已关联
            throw  new CustomException("当前分类下关联了套餐，不能删除");
        }
        System.out.println(count+"........."+count2);

        //正常删除
        categoryMapper.delete(id);

        return null;
    }

    @Override
    public List<Category> findByType(Category category) {
        if (category!=null){
            Integer type = category.getType();
            return categoryMapper.findByType(type);
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }
}
