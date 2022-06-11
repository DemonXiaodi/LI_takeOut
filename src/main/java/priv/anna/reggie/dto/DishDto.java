package priv.anna.reggie.dto;

import priv.anna.reggie.domain.Dish;
import priv.anna.reggie.domain.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于数据传输
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;


}
