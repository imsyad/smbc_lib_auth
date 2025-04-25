package com.smbc.library.auth_service.utils;

import com.smbc.library.auth_service.dto.ResponseDto;

public class ResponseUtil {

    public static <T> ResponseDto<T> success(int status, String message, T data) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setStatus(status);
        response.setMessage(message);
        response.setData(data);

        return response;
    }

    public static <T> ResponseDto<T> failed(int status, String message) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setStatus(status);
        response.setMessage(message);

        return response;
    }
}
