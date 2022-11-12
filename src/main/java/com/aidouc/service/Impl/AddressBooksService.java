package com.aidouc.service.Impl;

import com.aidouc.dao.AddressBooksDao;
import com.aidouc.domain.AddressBook;
import com.aidouc.service.AddressBooksInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class AddressBooksService extends ServiceImpl<AddressBooksDao, AddressBook>implements AddressBooksInterface {
}
