package com.aidouc.controller;

import com.aidouc.common.Result;
import com.aidouc.domain.Employee;
import com.aidouc.service.Impl.EmployeeService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    /**
     * @params employee
     * @return
     */
    public Result login(HttpServletRequest request, HttpServletResponse response, @RequestBody Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> lwt = new LambdaQueryWrapper<>();
        lwt.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeService.getOne(lwt);
        if (one == null) {
            Result<Object> list = Result.error("用户名不存在");
            return Result.error("用户名不存在");
        }
        System.out.println(one.getPassword());

        if (!password.equals(one.getPassword())) {
            return Result.error("密码错误");
        }
        if (one.getStatus() == 0) {
            return Result.error("当前用户已经失效");
        }

        request.getSession().setAttribute("employee", one.getId());

        return Result.success(one);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Object> getAll(int page, int pageSize, String name) {
        log.info(page + "" + pageSize + "" + name);
        Page pagetion = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> lw = new LambdaQueryWrapper<>();
        lw.like(name != null, Employee::getName, name);
        lw.orderByAsc(Employee::getUpdateTime);
        employeeService.page(pagetion, lw);
        return Result.success(pagetion);
    }

    /**
     * @param employee
     * @return
     */
    @PostMapping
    public Result save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("员工信息" + employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("1234567890".getBytes()));
        employeeService.save(employee);
        return Result.success("创建成功");
    }

    @PutMapping
    public Result<String> updateStatus(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.getId() + "id和status" + employee.getStatus());
        LambdaQueryWrapper<Employee> lw = new LambdaQueryWrapper<>();
        lw.eq(Employee::getId, employee.getId());
        employeeService.update(employee, lw);
        return Result.success("状态已经改变");
    }

    @GetMapping("/{id}")
    public Result<Employee> getDetail(@PathVariable Long id) {
        LambdaQueryWrapper<Employee> lw = new LambdaQueryWrapper<>();
        lw.eq(Employee::getId, id);
        Employee one = employeeService.getOne(lw);
        return Result.success(one);
    }
}
