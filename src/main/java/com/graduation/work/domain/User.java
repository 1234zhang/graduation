package com.graduation.work.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private Byte state;
    private Timestamp updateTime;
    private Timestamp createTime;
}
