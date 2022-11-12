package com.aidouc.service.Impl;

import com.aidouc.dao.OderDetailDao;
import com.aidouc.domain.OrderDetail;
import com.aidouc.service.OrderDeailInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService extends ServiceImpl<OderDetailDao, OrderDetail>implements OrderDeailInterface {
}
