package com.viodb.core;

import lombok.Data;

@Data
public class ApiResponse<T> {
    // response to the frontend

    // 200 indicates success; 500 indicates error
    private int statusCode;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return success("success", data);
    }

    public static <T> ApiResponse<T> success(String meg, T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.statusCode = 200;
        r.message = meg;
        r.data = data;
        return r;
    }

    public static <T> ApiResponse<T> error(String meg) {
        ApiResponse<T> r = new ApiResponse<>();
        r.statusCode = 500;
        r.message = meg;
        return r;
    }
}