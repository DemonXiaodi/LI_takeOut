package priv.anna.reggie.mapper;


import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.SetmealDish;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 保存菜品关系信息
     */
    Boolean save(SetmealDish setmealDish);


    /**
     * 根据套餐id查询套餐菜品关系对象
     */
    List<SetmealDish> findBySetmealId(Long setmealId);


    /**
     * 删除套餐菜品关系信息
     */
    Boolean delete(Long setmealId);
}
