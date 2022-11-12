package com.aidouc.service.Impl;

import com.aidouc.dao.DishFlavorDao;
import com.aidouc.domain.DishFlavor;
import com.aidouc.service.DishFlavorInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFalvorService extends ServiceImpl<DishFlavorDao, DishFlavor> implements DishFlavorInterface {
}
