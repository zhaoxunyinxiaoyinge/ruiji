package com.aidouc.service.Impl;
import com.aidouc.common.Result;
import com.aidouc.dao.OrderDao;
import com.aidouc.domain.Orders;
import com.aidouc.service.OrderInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends ServiceImpl<OrderDao, Orders> implements OrderInterface {
    @Override
    public Result<String> getOrder(Orders orders) {
// 获取用户useid,


// 获取当前订单的地址

//自动生成订单

// 根据订单生成计算金额

// 生成订单详情页面的数据

// 保存订单数据
// 保存订单详情页面数据

// 清空当前用户的购物车数据
        return null;
    }
}
