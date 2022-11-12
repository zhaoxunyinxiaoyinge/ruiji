package com.aidouc.service;

import com.aidouc.common.Result;
import com.aidouc.domain.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OrderInterface extends IService<Orders> {
    public Result<String> getOrder(Orders orders);
}
