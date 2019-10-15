package com.zack.gojektestjava.util;

public class Response<T> {
    private T data;
    private String message;
    private Status status;

    public enum Status {
        SUCCESS, ERROR
    }

    public Response(T data, Status status, String message) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data, Status.SUCCESS, null);
    }

    public static <T> Response<T> error(String msg) {
        return new Response<>(null, Status.ERROR, msg);
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }
}
