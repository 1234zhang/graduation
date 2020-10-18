package com.graduation.work.domain;

import lombok.Data;

@Data
public class Response<T> {
    private Boolean isSuccess;
    private String message;
    private T data;

    public Response() {
        isSuccess = true;
        message = "成功";
    }

    public Response(Boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
    public Response(Boolean isSuccess, String message, T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }
}
