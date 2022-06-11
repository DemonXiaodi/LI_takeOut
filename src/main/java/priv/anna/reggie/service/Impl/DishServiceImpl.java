package priv.anna.reggie.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import priv.anna.reggie.common.CustomException;
import priv.anna.reggie.domain.Category;
import priv.anna.reggie.domain.Dish;
import priv.anna.reggie.domain.DishFlavor;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.dto.DishDto;
import priv.anna.reggie.mapper.CategoryMapper;
import priv.anna.reggie.mapper.DishFlavorMapper;
import priv.anna.reggie.mapper.DishMapper;
import priv.anna.reggie.service.DishFlavorService;
import priv.anna.reggie.service.DishService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public int findCountByCategoryId(Long id) {
        return dishMapper.findCountByCategoryId(id);
    }

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */

    @Override
    @Transactional //事务控制
    public void saveWithFlavor(DishDto dishDto,Long empId) {
        System.out.println("empId:"+empId);

        //保存菜品的基本信息到菜品 dish表
        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setCreateUser(empId);
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setUpdateUser(empId);
        dishMapper.saveDish(dishDto);

        Long dishId = dishMapper.findIdByName(dishDto.getName()); //菜品id
        System.out.println(dishId);

        //保存菜品口味数据到菜品口味表
        List<DishFlavor> flavors = dishDto.getFlavors();
        System.out.println(flavors);

        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
            System.out.println(flavor);
            boolean r = dishFlavorService.save(flavor, empId);
            System.out.println(r);


        }

    }


    /**
     * 菜品信息分页查询
     * 最终要返回Page<DishDto>
     * */
    public Page<DishDto> page(int page,int pageSize,String name){

        //开启分页
        PageHelper.startPage(page,pageSize);//传入当前页、每页的数量
        //设定排序规律，按sort字段排序，升序
        PageHelper.orderBy("sort asc");

        //查询
        List<Dish> allDish;
        if (name != null){
            allDish = dishMapper.findByName(name);
        }else {
            allDish = dishMapper.findAll();
        }

        //查询结果交给分页助手
        PageInfo<Dish> pageInfo= new PageInfo<Dish>(allDish);

        //把真正的List<Dish>遍历出来再另外封装
        List<Dish> list = pageInfo.getList();
        List<Dish> realList = new ArrayList<Dish>(list);  //查询到的Dish集合结果，也就是records

        //要返回的结果，包装成自己写的page类，只要records和total
        Page<Dish> page1= new Page<Dish>();
        page1.setTotal(pageInfo.getTotal());
        page1.setRecords(realList);


        //对象拷贝，将查询出的Dish对象的属性拷贝到DishDto，忽略records
        Page<DishDto> dishDtoPage = new Page<>();
        BeanUtils.copyProperties(page1,dishDtoPage,"records");

        //把 List<Dish> realList 处理成List<DishDto>
        List<DishDto> dtoList = realList.stream().map((item)->{  //拉姆达表达式，item代表遍历出的每一个Dish
            //获得分类id
            Long categoryId = item.getCategoryId();
            //用分类id查出分类对象，再获得分类名
            Category category = categoryMapper.findById(categoryId);
            String categoryName = category.getName();

            DishDto dishDto = new DishDto();
            dishDto.setCategoryName(categoryName);

            //对象拷贝
            BeanUtils.copyProperties(item,dishDto);

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dtoList);

        return dishDtoPage;
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     */
    public DishDto get(Long id){
        DishDto dish = dishMapper.findById(id);
        List<DishFlavor> dishFlavors = dishFlavorMapper.findByDishId(id);
        dish.setFlavors(dishFlavors);

        return dish;
    }


    /**
     * 修改菜品
     * 修改菜品涉及到口味表和菜品表
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto,Long empId) {
        //更新菜品表
        dishDto.setUpdateTime(LocalDateTime.now());
        dishDto.setUpdateUser(empId);
        dishMapper.update(dishDto);

        //清理当前口味数据
        dishFlavorMapper.deleteById(dishDto.getId());

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDto.getId());

            //添加当前提交过来的口味
            flavor.setCreateTime(LocalDateTime.now());
            flavor.setCreateUser(empId);
            flavor.setUpdateTime(LocalDateTime.now());
            flavor.setUpdateUser(empId);
            dishFlavorMapper.saveFlavor(flavor);
        }
    }


    /**
     * 根据条件(id)查询菜品信息
     * @param dish
     * @return
     */
    @Override
    public List<DishDto> findBy(Dish dish) {
        //根据条件(id)查询菜品信息
        List<Dish> list = dishMapper.findBy(dish);

        List<DishDto> dtolist = new ArrayList<>();

        //对象拷贝
        for (Dish dish1 : list) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish1,dishDto);
            //查询菜品的口味数据
            List<DishFlavor> dishFlavors = dishFlavorMapper.findByDishId(dish1.getId());
            dishDto.setFlavors(dishFlavors);
            dtolist.add(dishDto);
        }

        return dtolist;
    }


    /**
     * 删除或批量删除菜品
     * @param ids
     */
    @Override
    public void removeWithFlavor(List<Long> ids) {
    //1. 查询菜品状态，确定是否可以删除
        Boolean flag = true;  //是否能删除的标记
        for (Long id : ids) {
            Integer status = dishMapper.findStatus(id);
            if (status == 1){
                flag = false;
            }
        }

    //2.如果不能删除，抛出一个业务异常
        if (!flag){
            throw  new CustomException("菜品正在售卖中，不能删除");
        }
        else {  //如果可以删除
            for (Long id : ids) {
                //删除菜品数据
                dishMapper.delete(id);
                //删除套餐菜品关联数据
                dishFlavorMapper.deleteById(id);

            }

        }

    }


    /**
     * 启售、停售、批量启售、批量停售
     * @param ids
     */
    @Override
    public void starStopSale(int status,List<Long> ids) {
        if(status ==  1){  //启售
            log.info("菜品启售");
            for (Long id : ids) {
                dishMapper.startSale(id);
            }

        }
       else {
            log.info("菜品停售");
            for (Long id : ids){
                dishMapper.stopSale(id);
            }
        }

    }
}
