package com.aidouc.service.Impl;

import com.aidouc.dao.ShopingCartDao;
import com.aidouc.domain.ShoppingCart;
import com.aidouc.service.ShopingCartInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShopingCartService extends ServiceImpl<ShopingCartDao, ShoppingCart> implements ShopingCartInterface {
}
