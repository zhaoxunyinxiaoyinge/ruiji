package com.aidouc.service;

import com.aidouc.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInterface extends IService<User> {
}
