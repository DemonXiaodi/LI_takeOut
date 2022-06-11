package priv.anna.reggie.service;

import org.springframework.stereotype.Service;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.dto.SetmealDto;

import java.util.List;


public interface SetmealService {
    int findCountByCategoryId(Long id);


    /**
     * 新增套餐，保存套餐菜品关系
     */
    void saveWithDish(SetmealDto setmealDto,Long empId);


    /**
     * 套餐信息分页查询
     */
    Page page(int page,int pageSize,String name);

    /**
     * 套餐信息回显
     */
    SetmealDto get(Long id);


    /**
     * 修改套餐信息
     */
    void update(SetmealDto setmealDto,Long empId);

    /**
     * 删除套餐数据，删除套餐菜品关联数据
     * @param ids
     */
    void removeWithDish(List<Long> ids);

    /**
     * 启售、停售、批量启售、批量停售
     */
    void starStopSale(int status,List<Long> ids);


    /**
     * 移动端展示套餐信息
     */
    List<SetmealDto> list(Long categoryId,Integer status);
}

