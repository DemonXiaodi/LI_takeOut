package priv.anna.reggie.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.anna.reggie.common.CustomException;
import priv.anna.reggie.domain.*;
import priv.anna.reggie.dto.SetmealDto;
import priv.anna.reggie.mapper.CategoryMapper;
import priv.anna.reggie.mapper.SetmealDishMapper;
import priv.anna.reggie.mapper.SetmealMapper;
import priv.anna.reggie.service.SetmealDishService;
import priv.anna.reggie.service.SetmealService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;



    @Override
    public int findCountByCategoryId(Long id) {
        return setmealMapper.findCountByCategoryId(id);
    }

    /**
     * 新增套餐，并且保存套餐菜品关系
     * 这里又是不能同时事务控制，不然查不到套餐id
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto,Long empId) {
        //保存套餐的基本信息
        setmealDto.setCreateTime(LocalDateTime.now());
        setmealDto.setCreateUser(empId);
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealDto.setUpdateUser(empId);
        setmealMapper.save(setmealDto);

        //获取套餐id
        Long dishId = setmealMapper.findIdByName(setmealDto.getName());


        //保存套餐和菜品的关联信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
//        for (SetmealDish setmealDish : setmealDishes) {
//            setmealDish.setSetmealId(dishId);
//            setmealDishService.save(setmealDish);
//        }

        //可以用stream流代替for循环
        setmealDishes.stream().map((item)->{
            item.setSetmealId(dishId);
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            item.setCreateUser(empId);
            item.setUpdateUser(empId);
            setmealDishService.save(item);
            return item;
        }).collect(Collectors.toList());

    }


    /**
     * 套餐信息分页查询
     */
    @Override
    public Page page(int page, int pageSize, String name) {
        //开启分页查询
        PageHelper.startPage(page,pageSize);
        //设定排序规律，按sort字段排序，升序
//        PageHelper.orderBy("sort asc");

        //查询
        List<SetmealDto> SetmealDtos;
        if (name != null){
            SetmealDtos = setmealMapper.findByName(name);
        }else {
            SetmealDtos = setmealMapper.findAll();
        }

        //setmealDto中放入分类名
        for (SetmealDto setmealDto : SetmealDtos) {
            //分类id查出分类名
            Category category = categoryMapper.findById(setmealDto.getCategoryId());
            setmealDto.setCategoryName(category.getName());
        }

        //查询结果交给分页助手
        PageInfo<SetmealDto> pageInfo= new PageInfo<SetmealDto>(SetmealDtos);

        //把真正的List<SetmealDto>遍历出来再另外封装
        List<SetmealDto> list = pageInfo.getList();
        List<SetmealDto> realList = new ArrayList<SetmealDto>(list);  //查询到的SetmealDto集合结果，也就是records

        //要返回的结果，包装成自己写的page类
        Page<SetmealDto> page1 = new Page<SetmealDto>();
        page1.setTotal(pageInfo.getTotal());
        page1.setRecords(realList);


        return page1;
    }

    /**
     * 套餐信息回显
     */
    @Override
    public SetmealDto get(Long id) {
        SetmealDto byId = setmealMapper.findById(id);
        List<SetmealDish> bySetmealId = setmealDishMapper.findBySetmealId(id);
        byId.setSetmealDishes(bySetmealId);
        return byId;
    }

    /**
     * 修改套餐信息，修改两张表
     * @param setmealDto
     */
    @Override
    @Transactional
    public void update(SetmealDto setmealDto,Long empId) {
        log.info(setmealDto.toString());

        //修改套餐信息
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealDto.setUpdateUser(empId);
        setmealMapper.update(setmealDto);

        //删除套餐菜品关系信息
        setmealDishMapper.delete(setmealDto.getId());

        //新增套餐菜品关系信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            item.setCreateUser(empId);
            item.setUpdateUser(empId);
            setmealDishService.save(item);
            return item;
        }).collect(Collectors.toList());

    }


    /**
     * 删除套餐数据，删除套餐菜品关联数据
     * 批量删除，有一个在售卖都不能删除
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {

    //1. 查询套餐状态，确定是否可以删除
        Boolean flag = true;  //是否能删除的标记
        for (Long id : ids) {
            Integer status = setmealMapper.findStatus(id);
            if (status == 1) {
                //在售卖，不能删除
                flag = false;
            }
        }

    //2.如果不能删除，抛出一个业务异常
        if (!flag){
            throw  new CustomException("套餐正在售卖中，不能删除");
        }
        else {  //如果可以删除
            for (Long id : ids) {
                //删除套餐数据
                setmealMapper.delete(id);
                //删除套餐菜品关联数据
                setmealDishMapper.delete(id);
            }

        }

    }

    /**
     * 启售、停售、批量启售、批量停售
     * @param status
     * @param ids
     */
    @Override
    public void starStopSale(int status, List<Long> ids) {
        if (status == 1){  //启售
            log.info("菜品启售");
            for (Long id : ids) {
                setmealMapper.startSale(id);
            }
        }
        else {
            log.info("菜品停售");
            for (Long id : ids) {
                setmealMapper.stopSale(id);
            }
        }
    }


    /**
     * 移动端展示套餐信息
     * @param categoryId
     * @param status
     * @return
     */
    @Override
    public List<SetmealDto> list(Long categoryId, Integer status) {

        //通过分类查询该分类下所有起售的套餐
        List<SetmealDto> byCategoryId = setmealMapper.findByCategoryId(categoryId);

        //查询每个套餐的口味数据
        for (SetmealDto setmealDto : byCategoryId) {
            List<SetmealDish> dishs = setmealDishMapper.findBySetmealId(setmealDto.getId());
            setmealDto.setSetmealDishes(dishs);
        }

        return byCategoryId;
    }
}
