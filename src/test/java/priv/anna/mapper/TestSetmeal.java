package priv.anna.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.anna.reggie.ReggieApplication;
import priv.anna.reggie.domain.Dish;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.dto.SetmealDto;
import priv.anna.reggie.mapper.DishMapper;
import priv.anna.reggie.mapper.SetmealDishMapper;
import priv.anna.reggie.mapper.SetmealMapper;
import priv.anna.reggie.service.DishFlavorService;
import priv.anna.reggie.service.DishService;
import priv.anna.reggie.service.SetmealService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ReggieApplication.class)
public class TestSetmeal {


    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Test
    public void test1()
    {
        Dish dish = new Dish();

        dish.setCategoryId(Long.parseLong("1397844263642378242"));
        List<Dish> by = dishMapper.findBy(dish);

        System.out.println(by);
    }

    @Test
    public void test2(){
        Page ji = setmealService.page(1, 3, "A");
        System.out.println(ji);
    }

    @Test
    public void test3(){
        SetmealDto setmealDto = setmealService.get(Long.parseLong("1415580119015145479"));
        System.out.println(setmealDto);
        System.out.println(setmealDto.getImage());
    }

    @Test
    public void test4(){
        Boolean delete = setmealDishMapper.delete(Long.parseLong("1415580119015145484"));
        System.out.println(delete);
    }
    @Test
    public void test5(){
        SetmealDto setmealDto = new SetmealDto();
        setmealDto.setId(Long.parseLong("1415580119015145482"));
        setmealDto.setName("商务B");
        Boolean update = setmealMapper.update(setmealDto);
        System.out.println(update);
    }

    @Test
    public void test6(){
//        List<Long> ids = new ArrayList<>();
//        ids.add(Long.parseLong("1415580119015145486"));
//        ids.add(Long.parseLong("1415580119015145485"));
//        ids.add(Long.parseLong("1415580119015145487"));
//        Integer status = setmealMapper.findStatus(ids);
//        System.out.println(status);
    }
}
