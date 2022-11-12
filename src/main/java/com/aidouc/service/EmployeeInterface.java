package com.aidouc.service;

import com.aidouc.dao.EmployeeDao;
import com.aidouc.domain.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeInterface extends IService<Employee> {
}
