package priv.anna.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.Dish;
import priv.anna.reggie.dto.DishDto;

import java.util.List;

@Mapper
public interface DishMapper {

    int findCountByCategoryId(Long id);

    /**
     * 保存菜品的基本信息到菜品 dish表
     */
    boolean saveDish(DishDto dishDto);


    Long findIdByName(String name);

    /**
     * 查询所有菜品信息
     */
    List<Dish> findAll();

    /**
     * 按照菜名查询菜品信息
     */
    List<Dish> findByName(String name);

    /**
     * 根据 id 查询菜品信息
     */
    DishDto findById(Long id);

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    boolean update(DishDto dishDto);

    /**
     * 根据条件查询启售的菜品信息
     */
    List<Dish> findBy(Dish dish);


    /**
     * 根据菜品id查询菜品是否在售卖
     */
    Integer findStatus(Long id);


    /**
     * 删除菜品
     */
    Boolean delete(Long id);


    /**
     * 启售菜品
     */
    Boolean startSale(Long id);

    /**
     * 停售菜品
     */
    Boolean stopSale(Long id);

}
