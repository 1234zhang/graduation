package com.graduation.work.controller;

import com.graduation.work.domain.LoginInfo;
import com.graduation.work.domain.Response;
import com.graduation.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public  Response register(@RequestBody LoginInfo loginInfo) {
        return userService.register(loginInfo);
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginInfo loginInfo) {
        return userService.login(loginInfo);
    }

    @GetMapping("/loginout")
    public Response loginOut(String username) {
        return userService.loginOut(username);
    }
}
