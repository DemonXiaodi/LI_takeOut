package priv.anna.reggie.controller;

//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.itheima.reggie.common.BaseContext;
//import com.itheima.reggie.common.R;
//import com.itheima.reggie.entity.AddressBook;
//import com.itheima.reggie.service.AddressBookService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import priv.anna.reggie.common.BaseContext;
import priv.anna.reggie.common.R;
import priv.anna.reggie.domain.AddressBook;
import priv.anna.reggie.service.AddressBookService;

import java.util.List;

/**
 * 地址簿管理
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        log.info("addressBook:{}", addressBook);
        AddressBook addressBook1 = addressBookService.setDefault(addressBook);
        return R.success(addressBook1);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBook> getDefault() {

        Long userId = BaseContext.getCurrentId();

        AddressBook addressBook = addressBookService.findDefault(userId);
        
        if (null == addressBook) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);

        //SQL:select * from address_book where user_id = ? order by update_time desc

        List<AddressBook> result = addressBookService.findAllByUserId(addressBook);
        return R.success(result);
    }

    /**
     * 修改地址信息
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> update(AddressBook addressBook){
        log.info(addressBook.toString());

        addressBookService.update(addressBook);
        return R.success("修改成功");

    }

    @DeleteMapping
    public R<String> delete( Long ids){
        log.info(ids.toString());
        addressBookService.delete(ids);
        return R.success("删除成功");
    }



}
