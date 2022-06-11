package priv.anna.reggie.service;


import priv.anna.reggie.domain.Orders;
import priv.anna.reggie.domain.Page;

public interface OrderService {


    /**
     * 用户下单
     * @param orders
     */
    public void  submit(Orders orders);

    /**
     * 后台订单分页查询
     */
    Page<Orders> page(int page, int pageSize, String number);

    /**
     * 用户端订单分页查询
     */
    Page<Orders> userPage(int page, int pageSize);
}
