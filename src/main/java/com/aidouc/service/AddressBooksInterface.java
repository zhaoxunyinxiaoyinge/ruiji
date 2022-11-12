package com.aidouc.service;

import com.aidouc.domain.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBooksInterface extends IService<AddressBook> {
}
