package priv.anna.reggie.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.anna.reggie.common.BaseContext;
import priv.anna.reggie.common.CustomException;
import priv.anna.reggie.domain.*;
import priv.anna.reggie.mapper.*;
import priv.anna.reggie.service.OrderService;
import priv.anna.reggie.service.ShoppingCartService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 用户下单
     * @param orders
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
        //1. 获得当前用户的id
        Long userId = BaseContext.getCurrentId();

        //2. 查询当前用户的购物车数据
        List<ShoppingCart> sc = shoppingCartMapper.findByUserId(userId);

        if (sc ==  null || sc.size() ==0){
            throw new CustomException("购物车为空，不能下单");
        }

        //查询用户数据
        User user = userMapper.findById(userId);
        //查询地址数据
        AddressBook addressBook = addressBookMapper.findById(orders.getAddressBookId());
        if (addressBook ==  null){
            throw new CustomException("用户地址信息有误，不能下单");
        }

        //3. 向订单表插入一条数据

        //使用 时间（精确到毫秒）+随机数 生成订单号
        String number = OrderServiceImpl.getNumber();
        orders.setNumber(number);

        //计算订单总金额，生成明细的集合
        AtomicInteger amount = new AtomicInteger(0);  //总金额，AtomicInteger保证多线程计算安全
        List<OrderDetail> orderDetails = sc.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(Long.parseLong(number));
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(userId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(
            (addressBook.getProvinceName() == null ? "": addressBook.getProvinceName())
            + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
            + (addressBook.getDistrictName() == null ? "": addressBook.getDistrictName())
            + (addressBook.getDetail() == null ? "": addressBook.getDetail())
        );

        log.info("orders: {}",orders);

//        log.info("orderDetail: {}",orderDetails);

        // 向订单表插入一条数据
        orderMapper.save(orders);


        //4. 向订单明细表插入数据，多条数据
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailMapper.save(orderDetail);
        }

        //5. 清空购物车数据
        shoppingCartMapper.deleteByUserId(userId);
    }


    /**
     * 生成订单号
     * @return
     */
    public static String getNumber(){
        //时间（精确到毫秒）
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String localDate = LocalDateTime.now().format(ofPattern);
        //随机数
        String randomNumeric = RandomStringUtils.randomNumeric(2); //count：需要生成的随机串位数

        return localDate+randomNumeric;
    }

    /**
     * 订单信息分页查询
     */
    @Override
    public Page<Orders> page(int page, int pageSize, String number) {

        //开启分页
        PageHelper.startPage(page,pageSize);//传入当前页、每页的数量

        //查询
        List<Orders> all;
        if (number != null){
            //根据订单号number查询
            all = orderMapper.findByNumber(number);
        }
        else {
            //查询所有订单
            all = orderMapper.findAll();
        }
        //查询结果交给分页助手
        PageInfo<Orders> pageInfo= new PageInfo<Orders>(all);

        //把真正的List<Dish>遍历出来再另外封装
        List<Orders> list = pageInfo.getList();
        List<Orders> realList = new ArrayList<Orders>(list);  //查询到的Orders集合结果，也就是records

        //要返回的结果，包装成自己写的page类，只要records和total
        Page<Orders> page1= new Page<Orders>();
        page1.setTotal(pageInfo.getTotal());
        page1.setRecords(realList);

        return page1;
    }


    /**
     * 用户端订单分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Orders> userPage(int page, int pageSize) {
        //1. 获得当前用户的id
        Long userId = BaseContext.getCurrentId();

        //开启分页
        PageHelper.startPage(page,pageSize);//传入当前页、每页的数量

        //查询
        List<Orders> all = orderMapper.findByUserId(userId);

        //查询结果交给分页助手
        PageInfo<Orders> pageInfo= new PageInfo<Orders>(all);

        //把真正的List<Dish>遍历出来再另外封装
        List<Orders> list = pageInfo.getList();
        List<Orders> realList = new ArrayList<Orders>(list);  //查询到的Orders集合结果，也就是records

        //要返回的结果，包装成自己写的page类，只要records和total
        Page<Orders> page1= new Page<Orders>();
        page1.setTotal(pageInfo.getTotal());
        page1.setRecords(realList);

        return page1;

    }
}
