package priv.anna.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.anna.reggie.common.R;
import priv.anna.reggie.domain.Category;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品分类、新增套餐分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category, HttpServletRequest request) {
        log.info("category: {}", category);
        Long empId = (Long) request.getSession().getAttribute("employee");
        categoryService.save(category, empId);
        return R.success("新增分类成功");
    }

    /**
     * 菜品套餐分类的分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("page = {},pageSize = {}", page, pageSize);
        Page<Category> fenyepage = categoryService.page(page, pageSize);
        return R.success(fenyepage);
    }

    /**
     * 修改分类
     *
     * @param category
     * @param request
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category, HttpServletRequest request) {
        log.info("category: {}", category);
        Long empId = (Long) request.getSession().getAttribute("employee");
        categoryService.update(category, empId);
        return R.success("修改成功");
    }

    /**
     * 删除菜品套餐分类
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids) { //注意：这里前端的请求是ids
        log.info("删除分类，id：{}", ids);
        categoryService.delete(ids);
        return R.success("分类信息删除成功");
    }

    /**
     * 新增菜品中展示分类信息，根据条件查询分类数据
     *
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        log.info(category.toString()+"<<<<<<<<<,");

        List<Category> list1;

        //员工管理端的新增菜品的下拉框展示
        if (category.getType()!=null){
            list1 = categoryService.findByType(category);
        }
        // 移动端的请求，没有type，展示所有
        else {
            list1 = categoryService.findAll();
        }

        return R.success(list1);
    }


}
