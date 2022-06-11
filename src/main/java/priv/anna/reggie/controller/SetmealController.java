package priv.anna.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.anna.reggie.common.R;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.dto.DishDto;
import priv.anna.reggie.dto.SetmealDto;
import priv.anna.reggie.service.SetmealDishService;
import priv.anna.reggie.service.SetmealService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto, HttpServletRequest request) {
        log.info("套餐信息：{}", setmealDto.toString());

        Long empId = (Long) request.getSession().getAttribute("employee");
        setmealService.saveWithDish(setmealDto, empId);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        Page page1 = setmealService.page(page, pageSize, name);

        return R.success(page1);
    }


    /**
     * 修改套餐信息回显
     */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.get(id);
        return R.success(setmealDto);
    }

    /**
     * 修改套餐
     *
     * @param
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto, HttpServletRequest request) {
        log.info(setmealDto.toString());

        Long empId = (Long) request.getSession().getAttribute("employee");
        setmealService.update(setmealDto, empId);

        return R.success("修改菜品成功");
    }

    /**
     * 删除或批量删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {  //ids是套餐id
        log.info("ids: {}", ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

    /**
     * 启售、停售、批量启售、批量停售
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable int status, @RequestParam List<Long> ids) {
        log.info(String.valueOf(status));
        log.info(ids.toString());
        setmealService.starStopSale(status,ids);
        return R.success("操作成功");
    }

    /**
     * 移动端展示套餐信息
     */
    @GetMapping("/list")
    public R<List<SetmealDto>> list( Long categoryId,Integer status){
        log.info("categoryId:{},status:{}",categoryId.toString(),status);
        List<SetmealDto> list = setmealService.list(categoryId, status);

        return R.success(list);
    }

}
