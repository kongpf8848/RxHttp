package com.github.kongpf8848.rxhttp.converter;

import android.text.TextUtils;

import com.github.kongpf8848.rxhttp.exception.RxHttpException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

public class GsonConverter<T> implements IConverter<T> {
    @Override
    public T convert(final ResponseBody body, final Type type) throws Exception {
        T response = null;
        String result = body.string();
        body.close();
        if (TextUtils.isEmpty(result)) {
            throw new RxHttpException(RxHttpException.RXHTTP_CODE_RESPONSE_DATA_EMPTY_EXCEPTION, "response data is empty");
        }
        if (type == String.class) {
            response = (T) result;
        } else {
            try {
                response = new Gson().fromJson(result,type);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                throw new RxHttpException(RxHttpException.RXHTTP_CODE_JSON_PARSE_EXCEPTION, e.getMessage());
            }
        }
        if (response == null) {
            throw new RxHttpException(RxHttpException.RXHTTP_CODE_RESPONSE_NULL_EXCEPTION, "response bean is null");
        }
        return response;
    }
}
