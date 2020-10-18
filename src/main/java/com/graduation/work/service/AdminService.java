package com.graduation.work.service;

import com.graduation.work.domain.AdminRequest;
import com.graduation.work.domain.Response;
import com.graduation.work.domain.User;
import com.graduation.work.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    public Response<?> queryUserList(AdminRequest request) {
        if (isAdmin(request) == null) {
            List<User> userList = userMapper.selectUserList();
            return new Response<>(true, "查询成功", userList);
        }
        return isAdmin(request);
    }

    public Response<?> addUser(AdminRequest request) {
        if (isAdmin(request) == null) {
            if (isNull(request.getAddUser())) {
                return new Response<>(false, "新增用户账号和密码以及权限不能为空");
            }
            User user = request.getAddUser();
            user.setName(user.getUsername());
            user.setCreateTime(new Timestamp(System.currentTimeMillis()));
            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            User containUser = userMapper.selectUserByName(user.getUsername());
            if (containUser != null) {
                return new Response<>(false, "用户已经存在，不用新增");
            }
            if (userMapper.insertUser(user)) {
                return new Response<>(true, "用户新增成功");
            } else {
                return new Response<>(false, "用户新增失败");
            }
        }
        return isAdmin(request);
    }

    public Response<?> deleteUser(AdminRequest request) {
        if (isAdmin(request) == null) {
            if (request.getUpdateId() == null) {
                return new Response<>(false, "删除用户id不能为空");
            }
            if (userMapper.deleteUser(request.getUpdateId())) {
                return new Response<>(true, "用户删除成功");
            } else {
                return new Response<>(false, "用户删除失败");
            }
        } else {
            return isAdmin(request);
        }
    }

    public Response<?> updateUser(AdminRequest request) {
        if (isAdmin(request) == null) {
            User user = userMapper.selectUserById(request.getUpdateId());
            User updateUser = request.getAddUser();
            if (user == null) {
                return new Response<>(false, "更新的用户id不存在");
            }
            if (updateUser.getUsername() != null) {
                user.setUsername(updateUser.getUsername());
            }
            if (updateUser.getName() != null) {
                user.setName(updateUser.getName());
            }
            if (updateUser.getState() != null) {
                user.setState(updateUser.getState());
            }
            user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            if (userMapper.updateUser(user)) {
                return new Response<>(true, "用户信息更新成功");
            } else {
                return new Response<>(false, "用户信息更新失败");
            }
        } else {
            return isAdmin(request);
        }
    }

    private boolean isNull(User user) {
        return user.getState() == null || user.getPassword() == null || user.getUsername() == null;
    }

    private Response<?> isAdmin(AdminRequest request) {
        User admin = userService.loginState.get(request.getUsername());
        if (request.getUsername() == null || admin == null) {
            return new Response<>(false, "没有登录");
        }
        if ((int)admin.getState() != 1) {
            return new Response<>(false, "权限不足");
        }
        return null;
    }
}
