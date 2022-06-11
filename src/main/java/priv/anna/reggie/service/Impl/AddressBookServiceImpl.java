package priv.anna.reggie.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.anna.reggie.common.BaseContext;
import priv.anna.reggie.domain.AddressBook;
import priv.anna.reggie.mapper.AddressBookMapper;
import priv.anna.reggie.service.AddressBookService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增
     * @param addressBook
     */
    @Override
    public void save(AddressBook addressBook) {
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBook.setCreateUser(BaseContext.getCurrentId());
        addressBook.setUpdateUser(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBook.setIsDeleted(0);
        addressBookMapper.save(addressBook);
    }

    /**
     * 设置默认地址
     * @param addressBook
     */
    @Override
    public AddressBook setDefault(AddressBook addressBook) {

        Long id = addressBook.getId();

        //将该用户下的所有地址is_default改成0（非默认）
        addressBookMapper.setNonDefault(addressBook);

        //设置默认地址
        //SQL:update address_book set is_default = 1 where id = ?
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);

        return addressBook;
    }

    /**
     *  根据id查询地址
     * @param id
     * @return
     */
    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.findById(id);
    }


    /**
     * 查询默认地址
     * @param userId
     * @return
     */
    @Override
    public AddressBook findDefault(Long userId) {
        return addressBookMapper.findDefault(userId);
    }


    /**
     * 查询指定用户的全部地址
     * @param addressBook
     * @return
     */
    @Override
    public List<AddressBook> findAllByUserId(AddressBook addressBook) {
        return  addressBookMapper.findAllByUserId(addressBook);
    }


    /**
     * 修改地址信息
     * @param addressBook
     */
    @Override
    public void update(AddressBook addressBook) {
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBook.setUpdateUser(BaseContext.getCurrentId());
        addressBookMapper.update(addressBook);
    }


    /**
     * 删除地址信息
     * @param id
     */
    @Override
    public void delete(Long id) {
        addressBookMapper.delete(id);
    }
}
