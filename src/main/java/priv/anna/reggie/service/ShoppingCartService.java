package priv.anna.reggie.service;


import priv.anna.reggie.domain.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param shoppingCart
     */
    ShoppingCart add(ShoppingCart shoppingCart);

    /**
     * 删除购物车
     * @param shoppingCart
     */
    ShoppingCart sub(ShoppingCart shoppingCart);

    /**
     * 展示购物车
     */
    List<ShoppingCart> list();


    /**
     * 清空购物车
     */
    void delete();
}
