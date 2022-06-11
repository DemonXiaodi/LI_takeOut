package priv.anna.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.anna.reggie.common.R;
import priv.anna.reggie.domain.OrderDetail;
import priv.anna.reggie.domain.Orders;
import priv.anna.reggie.domain.Page;
import priv.anna.reggie.service.OrderService;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 后台订单分页查询
     * http://localhost:8080/order/page?page=1&pageSize=10&number=fgfg
     * @return
     */
    @GetMapping("/page")
    public R<Page<Orders>> page(int page, int pageSize, String number){
        log.info("page:{},pageSize:{},number{}",page,pageSize,number);
        Page<Orders> page1 = orderService.page(page, pageSize, number);
        return R.success(page1);
    }

    /**
     * 用户端订单分页查询
     * http://localhost:8080/order/userPage?page=1&pageSize=5
     */
    @GetMapping("/userPage")
    public R<Page<Orders>> userPage(int page, int pageSize){
        log.info("page:{},pageSize:{}",page,pageSize);
        Page<Orders> page1 = orderService.userPage(page, pageSize);
        return R.success(page1);
    }
}
