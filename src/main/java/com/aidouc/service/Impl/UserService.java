package com.aidouc.service.Impl;

import com.aidouc.dao.UserDao;
import com.aidouc.domain.User;
import com.aidouc.service.UserInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserDao, User> implements UserInterface {
}
