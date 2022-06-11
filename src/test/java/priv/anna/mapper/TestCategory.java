package priv.anna.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.anna.reggie.ReggieApplication;
import priv.anna.reggie.domain.Category;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.mapper.CategoryMapper;
import priv.anna.reggie.service.CategoryService;

import java.util.List;

@SpringBootTest(classes = ReggieApplication.class)
public class TestCategory {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void test1(){
        Category category = new Category();
        category.setType(1);
        category.setName("西餐");
        category.setSort(13);

        long id1 = 1;
        try {
            Boolean save = categoryService.save(category, id1);
            System.out.println(save);
        }catch (Exception e){
            System.out.println("同名");
        }


    }

    @Test
    public void test2(){
        Page<Category> page = categoryService.page(1, 3);
        System.out.println(page);
    }


    @Test
    public void test3(){
//        String i = "1413386191767674894";
//        long id = 1413386191767674894;
//        categoryService.delete()
    }

    @Test
    public void test4(){
//        Category category = new Category();
//        category.setType(1);
//        List<Category> byType = categoryService.findByType(category);
//        System.out.println(byType);

        List<Category> byType = categoryMapper.findByType(1);
        System.out.println(byType);
    }

}
