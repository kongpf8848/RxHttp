package com.github.kongpf8848.rxhttp.converter;

import java.lang.reflect.Type;
import okhttp3.ResponseBody;

public interface IConverter<T> {
    T convert(final ResponseBody body, final Type type) throws Exception;
}
