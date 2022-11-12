package com.aidouc.controller;


import com.aidouc.common.Result;
import com.aidouc.domain.User;
import com.aidouc.service.Impl.UserService;
import com.aidouc.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public Result<Integer> getMsg(@RequestBody User user, HttpSession session) {
        //生成验证码
        int code = ValidateCodeUtils.generateValidateCode(6);
        session.setAttribute(user.getPhone(), code);
        return Result.success(code);
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map map, HttpSession session) {
        log.info(map + "map");
        String phone = map.get("phone").toString();
        String code =  map.get("code").toString();
        Object sessionCode = session.getAttribute(phone);
        System.out.println(sessionCode+"比较"+code);
        System.out.println(sessionCode.toString());
        //如果登录成则进行下一步
        if (sessionCode != null && sessionCode.toString().equals(code)) {
            //查询表中是否承在
            LambdaQueryWrapper<User> lw = new LambdaQueryWrapper();
            lw.eq(User::getPhone, phone);
            User user = userService.getOne(lw);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return Result.success("登录成功");
        } else {
            return Result.error("验证码错误");
        }
    }
}
