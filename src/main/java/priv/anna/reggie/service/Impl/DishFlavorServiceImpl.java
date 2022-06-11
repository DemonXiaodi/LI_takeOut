package priv.anna.reggie.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.anna.reggie.domain.DishFlavor;
import priv.anna.reggie.mapper.DishFlavorMapper;
import priv.anna.reggie.service.DishFlavorService;

import java.time.LocalDateTime;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 保存口味
     */
    @Override
    public boolean save(DishFlavor dishFlavor,Long empId) {
        dishFlavor.setCreateTime(LocalDateTime.now());
        dishFlavor.setCreateUser(empId);
        dishFlavor.setUpdateTime(LocalDateTime.now());
        dishFlavor.setUpdateUser(empId);
        return dishFlavorMapper.saveFlavor(dishFlavor);
    }
}
