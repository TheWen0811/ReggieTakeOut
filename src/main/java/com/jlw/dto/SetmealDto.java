package com.jlw.dto;

import com.jlw.pojo.Setmeal;
import com.jlw.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
