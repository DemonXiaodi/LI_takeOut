package priv.anna.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.anna.reggie.domain.OrderDetail;

@Mapper
public interface OrderDetailMapper {

    /**
     * 保存明细
     */
    Boolean save(OrderDetail orderDetail);
}
