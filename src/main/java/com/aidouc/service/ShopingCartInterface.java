package com.aidouc.service;

import com.aidouc.domain.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PostMapping;

@Mapper
public interface ShopingCartInterface extends IService<ShoppingCart> {
}
