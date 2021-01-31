# RxHttp

轻量级的网络请求封装类库,基于RxJava2+Retrofit+OkHttp4.x实现，完美兼容MVVM架构，小巧精致，高度解耦，网络请求就是这么简单:smile::smile::smile:

# 亮点

+ 完美兼容MVVM,MVC架构,兼容Kotlin和Java,Kotlin+MVVM+RxHttp结合起来使用更酸爽

+ 完美解决泛型类型擦除的棘手问题，还原泛型的真实类型

+ 天生支持网络请求和Activity,Fragment生命周期绑定，界面销毁时自动取消网络请求回调

+ 天生支持多BaseUrl，支持动态传入Url

+ 支持自定义OkHttpClient.Builder及OkHttpClient,可高度自定义网络请求参数

+ 支持Glide和网络请求公用一个OkHttpClient，一个App一个OkHttpClient就足够了

+ 支持GET,POST,PUT,DELETE等请求方式,支持文件上传及进度监听，支持同时上传多个文件，支持Uri上传，兼容Android 10,11系统

+ 支持文件下载及进度监听，支持大文件下载，支持断点下载，支持文件MD5校验

+ 代码量极少,类库体积不足100kb,浓缩的都是精华_^_


# 使用

+ 添加依赖

在项目的根目录build.gradle文件中添加
```
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
在项目的模块目录build.gradle文件中添加
```
implementation 'com.github.kongpf8848:RxHttp:1.0.10'
```
+ 基础使用

```
   RxHttp.getInstance()
    /**
     * get:请求类型,可为get,post,put,delete,upload,分别对应GET/POST/PUT/DELETE/上传请求
     * context:上下文,可为Context,Activity或Fragment类型,当context为Activity或Fragment时网络请求和生命周期绑定
     */
    .get(context)
    /**
     * 请求url,如https://www.baidu.com
     */
    .url("xxx")
    /**
     *请求参数键值对，类型为Map,可为null,如hashMapOf("name" to "jack")
     */
    .params(map)
    /**
     *每个网络请求对应的tag值,可为null,用于后续手动根据tag取消指定网络请求
     */
    .tag("xxx")
    /**
     * HttpCallback:网络回调,参数xxx为返回数据对应的数据模型,
     * 类似RxJava中的Observer,onComplete只有在onNext回调之后执行,如发生错误则只会回调onError而不会执行onComplete
     */
    .enqueue(object : HttpCallback<xxx>() {
        /**
         * http请求开始时回调
         */
        override fun onStart() {

        }

        /**
         * http请求成功时回调
         */
        override fun onNext(response: xxx?) {

        }

        /**
         * http请求失败时回调
         */
        override fun onError(e: Throwable?) {

        }

        /**
         * http请求成功完成时回调
         */
        override fun onComplete() {

        }

        /**
         * 上传进度回调,请求方式为upload时才会回调
         */
        override fun onProgress(readBytes: Long, totalBytes: Long) {
        
        }
    })
```

# 强烈建议下载Demo,Demo中有详细的示例，演示MVVM及MVC架构如何使用RxHttp

# License
```
Copyright (C) 2019 kongpf8848

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
