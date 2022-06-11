package priv.anna.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.anna.reggie.ReggieApplication;
import priv.anna.reggie.domain.Orders;
import priv.anna.reggie.mapper.OrderMapper;
import priv.anna.reggie.service.Impl.OrderServiceImpl;
import priv.anna.reggie.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest(classes = ReggieApplication.class)
public class TestOrder {

    @Autowired
   private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Test
    public void test1(){
        Orders orders = new Orders();
//        orders.setId(Long.parseLong("123456777"));
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(300));
        orders.setUserId(Long.parseLong("2333"));
        orders.setUserName("sbsb");
        orders.setConsignee("sbsb");
        orders.setPhone("12345678909");
        orders.setAddressBookId(Long.parseLong("2444"));
        orders.setPayMethod(1);
        orders.setRemark("");
        orders.setAddress(
                "11"
                        + "广东"
                        + "haha"
                        + "dede"
        );

        Boolean save = orderMapper.save(orders);
        System.out.println(save);
    }

    @Test
    public void test2(){
        String number = OrderServiceImpl.getNumber();
        long ll = Long.parseLong(number);
        System.out.println(ll);
    }
}
