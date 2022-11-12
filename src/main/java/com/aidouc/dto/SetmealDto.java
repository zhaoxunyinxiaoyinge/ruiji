package com.aidouc.dto;

import com.aidouc.domain.Setmeal;
import com.aidouc.domain.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto  extends Setmeal {
    private List<SetmealDish> setmealDishes;
    private  String CategoryName;

}
