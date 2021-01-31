# RxHttp

轻量级的网络请求封装类库，基于RxJava2+Retrofit+OkHttp4.x实现，完美兼容MVVM架构，小巧精致，网络请求原来如此简单:smile::smile::smile:

# 亮点

+ 完美兼容MVVM，MVC架构，兼容Kotlin和Java，Kotlin+MVVM+RxHttp结合起来使用更酸爽

+ 完美解决泛型类型擦除的棘手问题，还原泛型的真实类型

+ 天生支持网络请求和Activity,Fragment生命周期绑定，界面销毁时自动取消网络请求回调

+ 天生支持多BaseUrl,支持动态传入Url

+ 支持自定义OkHttpClient.Builder及OkHttpClient，可高度自定义网络请求参数

+ 支持Glide和网络请求公用一个OkHttpClient，一个App一个OkHttpClient就足够了

+ 支持GET,POST,PUT,DELETE等请求方式，支持文件上传及进度监听，支持同时上传多个文件，支持Uri上传，兼容Android 10&11系统

+ 支持文件下载及进度监听，支持大文件下载，支持断点下载

+ 代码量极少，类库体积不足100kb，但足以胜任大部分APP的网络请求任务，浓缩的都是精华_^_

# 使用要求

  项目基于AndroidX,minSdkVersion>=21(Android 5.0)

# Gradle

+ 在项目的根目录build.gradle文件中添加：

  ```
  allprojects {
      repositories {
          jcenter()
          maven { url "https://jitpack.io" }
      }
  }
  ```

+ 在项目的模块目录build.gradle文件中添加：

  ```
  implementation 'com.github.kongpf8848:RxHttp:1.0.10'
  ```

# 配置(可选)

```
  RxHttpConfig.getInstance()
    /**
     * 失败重试次数
     */
    .maxRetries(3)
    /**
     * 每次失败重试间隔时间
     */
    .retryDelayMillis(200)
    /**
     * 自定义OkHttpClient.Builder()，RxHttp支持自定义OkHttpClient.Builder()，
     * 如不定义，则使用RxHttp默认的OkHttpClient.Builder()
     */
    .builder(OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        /**
         * DEBUG模式下，添加日志拦截器，建议使用RxHttp中的FixHttpLoggingInterceptor，使用OkHttp的HttpLoggingInterceptor在上传下载的时候会有IOException问题
         */
        if (BuildConfig.DEBUG) {
            addInterceptor(FixHttpLoggingInterceptor().apply {
                level = FixHttpLoggingInterceptor.Level.BODY
            })
        }
    })
```

# 基础使用

+ GET/POST/PUT/DELETE/上传请求

```
   RxHttp.getInstance()
    /**
     * get:请求类型，可为get,post,put,delete,upload，分别对应GET/POST/PUT/DELETE/上传请求
     * context:上下文，可为Context，Activity或Fragment类型，当context为Activity或Fragment时网络请求和生命周期绑定
     */
    .get(context)
    /**
     * 请求url，如https://www.baidu.com
     */
    .url("xxx")
    /**
     *请求参数键值对，类型为Map<String, Any?>?，如hashMapOf("name" to "jack")
     */
    .params(map)
    /**
     *每个网络请求对应的tag值，可为null，用于后续手动根据tag取消指定网络请求
     */
    .tag("xxx")
    /**
     * HttpCallback:网络回调，参数xxx为返回数据对应的数据模型，
     * 类似RxJava中的Observer，onComplete只有在onNext回调之后执行，如发生错误则只会回调onError而不会执行onComplete
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
         * 上传进度回调,请求类型为upload时才会回调
         */
        override fun onProgress(readBytes: Long, totalBytes: Long) {
        
        }
    })
```
+ 下载请求

```
 RxHttp.getInstance()
  /**
   * download:请求类型，下载请求
   * context:上下文，如不需要和生命周期绑定,应该传递applicationContext
   */
  .download(context)
  /**
   * 保存路径
   */
  .dir(dir)
  /**
   *保存文件名称
   */
  .filename(filename)
  /**
   * 是否为断点下载，默认为false
   */
  .breakpoint(true)
  /**
   * 下载地址，如http://study.163.com/pub/ucmooc/ucmooc-android-official.apk
   */
  .url(url)
  /**
   * 请求Tag
   */
  .tag(null)
  /**
   * 下载回调
   */
  .enqueue(object: DownloadCallback() {
      /**
       * 下载开始时回调
       */
      override fun onStart() {

      }

      /**
       * 下载完成时回调
       */
      override fun onNext(response: DownloadInfo?) {

      }

      /**
       * 下载失败时回调
       */
      override fun onError(e: Throwable?) {

      }

      /**
       * 下载完成之后回调
       */
      override fun onComplete() {

      }

      /**
       * 下载进度回调
       */
      override fun onProgress(readBytes: Long, totalBytes: Long) {

      }

  })

```


# 强烈建议下载Demo代码，Demo中有详细的示例，演示MVVM及MVC架构如何使用RxHttp

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
