package priv.anna.reggie.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.anna.reggie.domain.SetmealDish;
import priv.anna.reggie.mapper.SetmealDishMapper;
import priv.anna.reggie.service.SetmealDishService;

@Service
@Slf4j
public class SetmealDishServiceImpl implements SetmealDishService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     *保存菜品套餐关系
     */
    @Override
    public void save(SetmealDish setmealDish) {
        setmealDishMapper.save(setmealDish);
    }
}
