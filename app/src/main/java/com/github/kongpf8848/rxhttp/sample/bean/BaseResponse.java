package com.github.kongpf8848.rxhttp.sample.bean;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

    private String head;
    private T body;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
