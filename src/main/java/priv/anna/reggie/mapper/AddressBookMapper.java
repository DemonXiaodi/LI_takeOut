package priv.anna.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.AddressBook;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 新增
     */
    Boolean save(AddressBook addressBook);

    /**
     *根据id查询地址
     * @param id
     * @return
     */
    AddressBook findById(Long id);

    /**
     * 将该用户下的所有地址is_default改成0（非默认）
     * @param addressBook
     * @return
     */
    Boolean setNonDefault(AddressBook addressBook);

    /**
     * 修改地址信息
     */
    Boolean update(AddressBook addressBook);


    /**
     * 查询默认地址
     */
    AddressBook findDefault(Long userId);


    /**
     * 查询指定用户的全部地址
     */
    List<AddressBook> findAllByUserId(AddressBook addressBook);


    /**
     * 删除地址信息
     */
    Boolean delete(Long id);


}
