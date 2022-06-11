package priv.anna.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import priv.anna.reggie.domain.DishFlavor;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    boolean saveFlavor(DishFlavor dishFlavor);

    /**
     *根据 菜品id查询口味信息
     */
    List<DishFlavor> findByDishId(Long id);

    /**
     * 根据菜品id清理口味表
     */
    Boolean deleteById(Long dishId);



}
