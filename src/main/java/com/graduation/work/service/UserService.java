package com.graduation.work.service;

import com.graduation.work.domain.LoginInfo;
import com.graduation.work.domain.Response;
import com.graduation.work.domain.User;
import com.graduation.work.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    ConcurrentHashMap<String, User> loginState = new ConcurrentHashMap<>();

    public Response<?> register(LoginInfo loginInfo) {
        if (isNull(loginInfo)) {
            return new Response<>(false, "用户名或者密码不能为空");
        }
        User containUser = userMapper.selectUserByName(loginInfo.getUsername());
        if (containUser != null) {
            return new Response<>(false, "用户名已经存在");
        }
        User user = new User();
        user.setName(loginInfo.getUsername());
        user.setPassword(loginInfo.getPassword());
        user.setUsername(loginInfo.getUsername());
        user.setState((byte)0);
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boolean isSuccess = userMapper.insertUser(user);
        if (isSuccess) {
            return new Response<>(true, "注册成功");
        } else {
            return new Response<>(false, "注册失败");
        }
    }

    public Response<?> login(LoginInfo loginInfo) {
        if (isNull(loginInfo)) {
            return new Response<>(false, "用户名或者密码不能为空");
        }
        User loginUser = userMapper.selectUserByName(loginInfo.getUsername());
        if (loginUser == null) {
            return new Response<>(false, "该用户名没有注册");
        }
        if (!loginUser.getPassword().equals(loginInfo.getPassword())) {
            return new Response<>(false, "密码错误");
        }
        if (!loginState.containsKey(loginInfo.getUsername())) {
            loginState.put(loginInfo.getUsername(), loginUser);
        }
        return new Response<>(true, "登录成功");
    }

    public Response<?> loginOut(String username) {
        if (loginState.remove(username) != null) {
            return new Response<>(true, "成功退出登录");
        }
        return new Response<>(false, "退出登录失败");
    }
    private boolean isNull(LoginInfo loginInfo) {
        return loginInfo.getUsername() == null || loginInfo.getPassword() == null;
    }
}
