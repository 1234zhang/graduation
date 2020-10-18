package com.graduation.work.controller;

import com.graduation.work.domain.AdminRequest;
import com.graduation.work.domain.Response;
import com.graduation.work.domain.User;
import com.graduation.work.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;

    /**
     * 查看用户列表
     */

    @GetMapping("/queryUser")
    public Response<?> queryUser(@RequestBody AdminRequest request) {
        return adminService.queryUserList(request);
    }

    @PostMapping("/addUser")
    public Response<?> addUser(@RequestBody AdminRequest adminRequest) {
        return adminService.addUser(adminRequest);
    }

    @PostMapping("/deleteUser")
    public Response<?> deleteUser(@RequestBody AdminRequest admin) {
        return adminService.deleteUser(admin);
    }

    @PostMapping("/updateUser")
    public Response<?> updateUser(@RequestBody AdminRequest request) {
        return adminService.updateUser(request);
    }
}
