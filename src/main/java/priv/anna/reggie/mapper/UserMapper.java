package priv.anna.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.User;

@Mapper
public interface UserMapper {

    /**
     * 通过电话查询用户
     * @param phone
     * @return
     */
    User findUserByPhone(String phone);

    /**
     * 新增用户
     */
    Boolean save(User user);

    /**
     * 通过用户 id 查询用户信息
     */
    User findById(Long id);




}
