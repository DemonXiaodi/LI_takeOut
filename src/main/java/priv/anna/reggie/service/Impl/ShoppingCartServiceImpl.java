package priv.anna.reggie.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.anna.reggie.common.BaseContext;
import priv.anna.reggie.domain.ShoppingCart;
import priv.anna.reggie.mapper.ShoppingCartMapper;
import priv.anna.reggie.service.ShoppingCartService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        //设置userId，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        //同一个菜品点了两次，是修改number，而不是插入两次
        //判断口味是否相同？不相同就新增一条记录


        //查询当前菜品或者套餐是否在购物车中
        ShoppingCart sc = shoppingCartMapper.findExistsInShoppingCart(shoppingCart);

        if (sc != null) {  //说明菜品或套餐已经在购物车表中

            log.info(sc.toString());

            //数量number+1
            sc.setNumber(sc.getNumber() + 1);
            shoppingCartMapper.updateNumber(sc);

            return sc;
        } else { //不在购物车表中
            //插入
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);
            shoppingCartMapper.add(shoppingCart);
            ShoppingCart sc1 = shoppingCartMapper.findExistsInShoppingCart(shoppingCart);

            return sc1;

        }

    }


    /**
     * 减少购物车
     *
     * @param shoppingCart
     */
    @Override
    public ShoppingCart sub(ShoppingCart shoppingCart) {
        //设置userId，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);


        ShoppingCart sc = shoppingCartMapper.findExistsInShoppingCart(shoppingCart);
        if (sc != null) {
            Integer number = sc.getNumber();
            if (number > 0){
                sc.setNumber(number-1);
                shoppingCartMapper.updateNumber(sc);

                 return sc;
            }
            else {
                shoppingCartMapper.delete(shoppingCart);

            }
        }
            return null;
    }


    /**
     * 展示购物车
     * @return
     */
    @Override
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        log.info("userId:{}",userId);
        return shoppingCartMapper.findByUserId(userId);
    }

    /**
     * 清空购物车
     */
    @Override
    public void delete() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }
}
