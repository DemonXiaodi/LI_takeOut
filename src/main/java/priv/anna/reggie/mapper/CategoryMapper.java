package priv.anna.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 保存菜品分类信息
     * @param category
     * @return
     */
    Boolean save(Category category);

    /**
     * 查询所有
     * @return
     */
    List<Category> findAll();

    /**
     * 修改
     * @param category
     * @return
     */
    Boolean update(Category category);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Long id);

    List<Category> findByType(Integer type);

    /**
     * 通过分类id查询出分类对象
     * 用于菜品分页查询展示菜品分类
     * @param id
     * @return
     */
    Category findById(Long id);


}
