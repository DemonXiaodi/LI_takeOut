package priv.anna.reggie.mapper;


import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.Orders;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 向订单表插入一条数据
     */
    Boolean save(Orders orders);

    /**
     * 查询所有订单
     */
    List<Orders> findAll();

    /**
     * 根据订单号查询订单
     */
    List<Orders> findByNumber(String number);

    /**
     * 根据用户id查询订单
     */
    List<Orders> findByUserId(Long userId);

}
