package priv.anna.reggie.service;

import priv.anna.reggie.domain.Category;
import priv.anna.reggie.domain.Page;

import java.util.List;

public interface CategoryService {

    /**
     * 新增菜品套餐分类
     * @param category
     * @param empId
     * @return
     */
    public Boolean save(Category category,Long empId);

    /**
     * 分页排序查询
     * @param page
     * @param pageSize
     * @return
     */
    Page<Category> page(int page, int pageSize);

    /**
     * 修改
     * @param category
     * @param empId
     * @return
     */
    Boolean update(Category category,Long empId);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 新增菜品中展示分类信息，根据条件查询分类数据
     * @param category
     * @return
     */
    List<Category> findByType(Category category);


    /**
     * 查询所有分类对象
     */
    List<Category> findAll();


}
