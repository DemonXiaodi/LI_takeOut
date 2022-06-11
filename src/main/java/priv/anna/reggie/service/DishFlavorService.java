package priv.anna.reggie.service;

import priv.anna.reggie.domain.DishFlavor;

public interface DishFlavorService {

    /**
     * 保存口味
     */
    boolean save(DishFlavor dishFlavor,Long empId);

}
