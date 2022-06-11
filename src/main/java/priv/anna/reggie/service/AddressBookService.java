package priv.anna.reggie.service;

import priv.anna.reggie.domain.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * 新增地址薄
     * @param addressBook
     */
    void save( AddressBook addressBook);

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    AddressBook getById(Long id);

    /**
     * 设置默认地址
     * @param addressBook
     */
    AddressBook setDefault(AddressBook addressBook);

    /**
     * 查询默认地址
     */
    AddressBook findDefault(Long userId);


    /**
     * 查询指定用户的全部地址
     * @param addressBook
     * @return
     */
    List<AddressBook> findAllByUserId(AddressBook addressBook);

    /**
     * 修改地址信息
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 删除地址
     * @param id
     */
    void delete (Long id);


}
