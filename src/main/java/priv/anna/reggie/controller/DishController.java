package priv.anna.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.anna.reggie.common.R;
import priv.anna.reggie.domain.Dish;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.dto.DishDto;
import priv.anna.reggie.service.DishFlavorService;
import priv.anna.reggie.service.DishService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 菜品、菜品口味管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto, HttpServletRequest request){
        log.info(dishDto.toString());

        Long empId = (Long) request.getSession().getAttribute("employee");
        dishService.saveWithFlavor(dishDto,empId);
        return R.success("新增菜品成功");
    }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page pageDto = dishService.page(page, pageSize, name);

        return R.success(pageDto);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.get(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto, HttpServletRequest request){
        log.info(dishDto.toString());

        Long empId = (Long) request.getSession().getAttribute("employee");
        dishService.updateWithFlavor(dishDto,empId);
        return R.success("修改菜品成功");
    }

    /**
     * 新增菜单中添加菜品
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        return R.success( dishService.findBy(dish));
    }

    /**
     * 删除或批量删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){  //ids是套餐id
        log.info("ids: {}",ids);
        dishService.removeWithFlavor(ids);
        return R.success("删除成功");
    }

    /**
     * 启售、停售、批量启售、批量停售
     * @param status  0是停售，1是启售
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable int status,@RequestParam List<Long> ids){
        log.info(String.valueOf(status));
        log.info(ids.toString());

        dishService.starStopSale(status,ids);

        return R.success("操作成功");
    }

}
