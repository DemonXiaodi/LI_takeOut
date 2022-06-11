package priv.anna.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.anna.reggie.ReggieApplication;
import priv.anna.reggie.domain.Dish;
import priv.anna.reggie.domain.DishFlavor;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.dto.DishDto;
import priv.anna.reggie.mapper.DishMapper;
import priv.anna.reggie.service.DishFlavorService;
import priv.anna.reggie.service.DishService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ReggieApplication.class)
public class TestDish {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;

    @Autowired
    private DishMapper dishMapper;

    @Test
    void  test1(){
        DishFlavor dishFlavor = new DishFlavor();
        long id= 1;
        dishFlavor.setDishId(id);
        dishFlavor.setName("咸度");
        dishFlavor.setValue("非常咸");
        boolean save = dishFlavorService.save(dishFlavor,id);
        System.out.println(save);
    }

    @Test
    void  test2(){
        DishDto dishDto = new DishDto();
        dishDto.setName("咸味菜");
        dishDto.setCategoryId(Long.parseLong("1397844263642378242"));
        dishDto.setPrice( BigDecimal.valueOf(20));
        dishDto.setCode("4444");
        dishDto.setImage("lalala");
        dishDto.setDescription("wuwuwuw");
        dishDto.setStatus(1);

        DishFlavor dishFlavor = new DishFlavor();
        dishFlavor.setName("咸度");
        dishFlavor.setValue("非常咸");


        List<DishFlavor> list = new ArrayList<>();
        list.add(dishFlavor);

        dishDto.setFlavors(list);


        long empId = 1;
        dishService.saveWithFlavor(dishDto,empId);
    }

    @Test
    void  test3(){
//        List<Dish> all = dishMapper.findAll();
//        Page all = dishService.page(1, 3, "鸡");
//        System.out.println(all);

        Page<DishDto> all = dishService.page(1, 3, "鸡");
        List<DishDto> records = all.getRecords();
        for (DishDto d : records) {
            System.out.println(d.getCategoryName());
        }
    }

    @Test
    void  test4(){
        List<Dish> byName = dishMapper.findByName("鸡");
        System.out.println(byName);
    }


    @Test
    void  test5(){
        long id = Long.parseLong("1397849739276890114");
        DishDto m = dishService.get(id);
        System.out.println(m);
        System.out.println(m.getName());
    }
}
