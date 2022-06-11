package priv.anna.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import priv.anna.reggie.domain.ShoppingCart;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 查询当前菜品或套餐是否在该用户的购物车中
     */
    ShoppingCart findExistsInShoppingCart(ShoppingCart shoppingCart);


    /**
     * 添加购物车
     */
    Boolean add(ShoppingCart shoppingCart);

    /**
     * 删除购物车
     */
    Boolean delete(ShoppingCart shoppingCart);


    /**
     * 修改
     * @param shoppingCart
     * @return
     */
    Boolean update(ShoppingCart shoppingCart);


    /**
     * 修改number
     * @param
     * @return
     */
    Boolean updateNumber(ShoppingCart shoppingCart);


    /**
     * 通过用户id查询用户的购物车
     */
    List<ShoppingCart> findByUserId(Long userId);


    /**
     * 清空用户购物车
     */
    Boolean deleteByUserId(Long userId);







}
