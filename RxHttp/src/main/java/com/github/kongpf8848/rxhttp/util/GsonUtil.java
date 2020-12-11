package com.github.kongpf8848.rxhttp.util;

import android.text.TextUtils;

import com.google.gson.Gson;

public class GsonUtil {

    private static final Gson gson = new Gson();

    public static String toJson(Object obj){
        String gsonString = "";
        if (obj != null && !TextUtils.isEmpty(obj.toString())) {
            gsonString = gson.toJson(obj);
        }
        return gsonString;
    }
}
