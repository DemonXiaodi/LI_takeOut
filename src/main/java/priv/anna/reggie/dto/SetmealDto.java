package priv.anna.reggie.dto;

import priv.anna.reggie.domain.Setmeal;
import priv.anna.reggie.domain.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
