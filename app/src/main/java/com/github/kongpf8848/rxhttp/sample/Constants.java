package com.github.kongpf8848.rxhttp.sample;

import android.os.Environment;

import java.io.File;

public class Constants {

    public static final String LOG_TAG = "UULOG";

    public static final String URL_GET = "http://news-at.zhihu.com/api/4/stories/latest";
    //public static final String URL_DOWNLOAD = "http://192.168.0.106:8080/apk/com.happyjuzi.apps.juzi.apk";
    public static final String URL_DOWNLOAD = "http://study.163.com/pub/ucmooc/ucmooc-android-official.apk";
    public static final String BASE_URL_LOCAL = "http://192.168.0.101:8080/webtest/";
    public static final String URL_UPLOAD = BASE_URL_LOCAL+"upload";
    public static final String URL_POST_FORM = BASE_URL_LOCAL+"testForm";
    public static final String URL_POST = BASE_URL_LOCAL+"testPost";
    public static final String EXTERNAL_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator;
}
