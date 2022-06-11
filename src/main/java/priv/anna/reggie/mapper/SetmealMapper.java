package priv.anna.reggie.mapper;


import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.Setmeal;
import priv.anna.reggie.dto.SetmealDto;

import java.util.List;

@Mapper
public interface SetmealMapper {

    int findCountByCategoryId(Long categoryId);

    /**
     * 保存套餐信息
     */
    Boolean save(SetmealDto setmealDto);


    /*
    通过套餐名查询套餐id，套餐名是唯一的
     */
    Long findIdByName(String name);

    /**
     * 通过套餐名查询套餐信息,模糊查询
     */
    List<SetmealDto> findByName(String name);

    /**
     * 查询所有套餐信息
     */
    List<SetmealDto> findAll();


    /**
     * 通过id查询套餐信息
     */
    SetmealDto findById(Long id);


    /**
     * 修改套餐信息
     */
    Boolean update(SetmealDto setmealDto);


    /**
     * 根据套餐id删除套餐数据
     */
    Boolean delete(Long id);

    /**
     * 根据id查询套餐是否启售
     */
    Integer findStatus(Long id);

    /**
     * 启售套餐
     */
    Boolean startSale(Long id);


    /**
     * 停售套餐
     */
    Boolean stopSale(Long id);


    /**
     * 同过分类Id查询套餐信息
     */
    List<SetmealDto> findByCategoryId(Long categoryId);

}
