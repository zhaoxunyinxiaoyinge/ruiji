package com.aidouc.service.Impl;

import com.aidouc.dao.EmployeeDao;
import com.aidouc.domain.Employee;
import com.aidouc.service.EmployeeInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends ServiceImpl<EmployeeDao, Employee> implements EmployeeInterface {
}
