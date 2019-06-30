package com.github.kongpf8848.rxhttp.sample.bean;

import java.io.Serializable;

public class UploadResponse implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
