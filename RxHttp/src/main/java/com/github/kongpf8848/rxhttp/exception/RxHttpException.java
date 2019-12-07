package com.github.kongpf8848.rxhttp.exception;

public class RxHttpException extends Exception {

    public static final int RXHTTP_CODE_OK = 800;
    public static final int RXHTTP_CODE_EXCEPTION = RXHTTP_CODE_OK + 1;
    public static final int RXHTTP_CODE_RESPONSE_DATA_EMPTY_EXCEPTION = RXHTTP_CODE_EXCEPTION + 2;
    public static final int RXHTTP_CODE_JSON_PARSE_EXCEPTION = RXHTTP_CODE_EXCEPTION + 3;
    public static final int RXHTTP_CODE_RESPONSE_NULL_EXCEPTION = RXHTTP_CODE_EXCEPTION + 4;
    public static final int RXHTTP_CODE_CONNECTION_EXCEPTION = RXHTTP_CODE_EXCEPTION + 5;

    private int code;
    private String message;

    public RxHttpException(int code, String message) {
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
        return "RxHttpException[code=" + code + ",message=" + message + "]";
    }
}
