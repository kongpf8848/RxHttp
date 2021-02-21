package com.github.kongpf8848.rxhttp.typebuilder;

import com.github.kongpf8848.rxhttp.typebuilder.exception.TypeException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class TypeToken<T> {
    private final Type type;

    public TypeToken() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new TypeException("No generics found!");
        }
        ParameterizedType type = (ParameterizedType) superclass;
        this.type = type.getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }
}
