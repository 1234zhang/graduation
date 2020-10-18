package com.graduation.work.domain;

import lombok.Data;

@Data
public class AdminRequest {
    private String username;
    private Long updateId;
    private User addUser;
}
