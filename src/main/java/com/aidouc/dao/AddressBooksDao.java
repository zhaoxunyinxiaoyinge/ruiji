package com.aidouc.dao;

import com.aidouc.domain.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBooksDao extends BaseMapper<AddressBook> {
}
