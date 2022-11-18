package com.aidouc.controller;


import com.aidouc.common.Result;
import com.aidouc.domain.User;
import com.aidouc.service.Impl.UserService;
import com.aidouc.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/sendMsg")
    public Result<Integer> getMsg(@RequestBody User user, HttpSession session) {
        if(user.getPhone()!=null){
            //生成验证码
            int code = ValidateCodeUtils.generateValidateCode(6);
//            session.setAttribute(user.getPhone(), code);
            //设置阎验证码到服redis中进行设置 时间为5分钟
            redisTemplate.opsForValue().set(user.getPhone(),code,5, TimeUnit.MINUTES);

            return Result.success(code);
        }
        return Result.error("短线发送失败");
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map map, HttpSession session) {
        log.info(map + "map");
        String phone = map.get("phone").toString();
        String code =  map.get("code").toString();
         Object codes= redisTemplate.opsForValue().get(phone);
        //如果登录成则进行下一步
        if (codes != null && codes.toString().equals(code)) {
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

            //删除验证码
            redisTemplate.delete(phone);

            return Result.success("登录成功");
        } else {
            return Result.error("验证码错误");
        }
    }
}
