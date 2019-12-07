package com.github.kongpf8848.rxhttp.exception;

public class HttpError extends Exception {

    public static final int RXHTTP_CODE_OK =800;
    public static final int RXHTTP_CODE_ERROR = RXHTTP_CODE_OK+1;
    public static final int RXHTTP_CODE_RESPONSE_DATA_EMPTY_EXCEPTION = RXHTTP_CODE_ERROR+2;
    public static final int RXHTTP_CODE_JSON_PARSE_EXCEPTION =RXHTTP_CODE_ERROR+3;
    public static final int RXHTTP_CODE_RESPONSE_NULL_EXCEPTION =RXHTTP_CODE_ERROR+4;

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
