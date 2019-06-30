package com.github.kongpf8848.rxhttp.callback;

import com.github.kongpf8848.rxhttp.util.TypeUtil;
import java.lang.reflect.Type;

public abstract class HttpCallback<T> implements BaseCallback<T> {
    private Type type;

    public HttpCallback() {
        this.type = TypeUtil.getType(getClass());
    }

    public Type getType() {
        return type;
    }

}
