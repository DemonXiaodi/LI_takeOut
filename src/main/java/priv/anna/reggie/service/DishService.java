package priv.anna.reggie.service;

import org.springframework.stereotype.Service;
import priv.anna.reggie.domain.Dish;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.dto.DishDto;

import java.util.List;


public interface DishService {

    int findCountByCategoryId(Long id);

    /**
     * 新增菜品，同时插入菜品对应的口味数据，需要操作两张表，dish, dish_flavor
     */
    public void saveWithFlavor(DishDto dishDto,Long empId);


    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    public Page page(int page, int pageSize, String name);

    /**
     * 根据id查询菜品信息和对应的口味信息
     */
    public DishDto get(Long id);

    /**
     * 修改菜品
     */
    public void updateWithFlavor(DishDto dishDto,Long empId);

    /**
     * 根据条件查询菜品信息
     */
    public List<DishDto> findBy(Dish dish);

    /**
     * 删除或批量删除菜品
     */
    void removeWithFlavor (List<Long> ids);

    /**
     * 启售、停售、批量启售、批量停售
     */
    void starStopSale(int status,List<Long> ids);
}
