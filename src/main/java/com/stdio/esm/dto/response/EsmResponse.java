package com.stdio.esm.dto.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EsmResponse {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static final String CODE_KEY = "code";
    public static final String STATUS_KEY = "status";
    public static final String MESSAGE_KEY = "message";
    public static final String RESPONSE_DATA_KEY = "responseData";

    private int code;
    private String status;
    private String message;
    private Object responseData;

    public EsmResponse() {
        this.code = 0;
        this.status = STATUS_KEY;
        this.message = MESSAGE_KEY;
        this.responseData = null;
    }

    public EsmResponse(int code, String status, String message, Object responseData) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.responseData = responseData;
    }

    public Map<String, Object> getResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put(CODE_KEY, this.code);
        response.put(STATUS_KEY, this.status);
        response.put(MESSAGE_KEY, this.message);
        response.put(RESPONSE_DATA_KEY, this.responseData);
        return response;
    }

    public void setResponse(int code, String status, String message, Object responseData) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.responseData = responseData;
    }

}
