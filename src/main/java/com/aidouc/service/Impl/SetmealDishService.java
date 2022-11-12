package com.aidouc.service.Impl;

import com.aidouc.dao.SetmealDishDao;
import com.aidouc.domain.SetmealDish;
import com.aidouc.service.SetmealDishInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishService extends ServiceImpl<SetmealDishDao, SetmealDish> implements SetmealDishInterface {
}
