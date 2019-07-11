package com.github.kongpf8848.rxhttp.exception;

public class HttpError extends Exception {

    public static final int CODE_DATA_EXCEPTION = 6;
    public static final int CODE_DATE_EMPTY = 7;
    public static final int CODE_PARSE_EXCEPTION = 8;
    public static final int CODE_DOWNLOAD_EXCEPTION = 801;

    private int code;
    private String message;

    public HttpError(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return "HttpError[code=" + code + ",message=" + message + "]";
    }
}
