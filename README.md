# RxHttp

基于RxJava2+Retrofit 2.9.0+OkHttp 4.9.0实现的轻量级，完美兼容MVVM架构的网络请求封装类库，小巧精致，简单易用，网络请求原来如此简单:smirk::smirk::smirk:

# 亮点

+ 代码量极少，类库体积不足100kb，但足以胜任大部分APP的网络请求任务，浓缩的都是精华_^_

+ 完美兼容MVVM，MVC架构，兼容Kotlin和Java，Kotlin+MVVM+RxHttp结合起来使用更酸爽，MVVM官方推荐，抱紧Google大腿就对了

+ 完美解决泛型类型擦除的棘手问题，还原泛型的真实类型

+ 天生支持网络请求和Activity，Fragment生命周期绑定，界面销毁时自动取消网络请求回调

+ 天生支持多BaseUrl，支持动态传入Url

+ 支持自定义OkHttpClient.Builder，可高度自定义网络请求参数

+ 支持Glide等和网络请求公用一个OkHttpClient，充分利用OkHttpClient的线程池和连接池，大部分情况下一个App一个OkHttpClient就够了

+ 支持GET，POST，PUT，DELETE等请求方式，支持文件上传及进度监听，支持同时上传多个文件，支持Uri上传，兼容Android 10&11系统

+ 支持文件下载及进度监听，支持大文件下载，支持断点下载


# 使用要求

项目基于AndroidX，Java8+，minSdkVersion>=21

# 使用

  ```groovy
  implementation 'com.github.kongpf8848:RxHttp:1.0.11'
  ```

# 配置(可选)

```kotlin
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

```kotlin
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

```kotlin
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
+ 取消请求

```kotlin
    /**
     * tag:Any?,请求Tag,对应网络请求里的Tag值
     * 如不为null,则取消指定网络请求,
     * 如为null，则取消所有网络请求
     */
    RxHttp.getInstance().cancelRequest(tag)
```
# 项目实战

此处假设服务端返回的数据格式为{"code":xxx,"data":T,"msg":""}，其中code为响应码，整型，等于200时为成功，其余为失败，data对应的数据类型为泛型(boolean，int，double，String，对象{ }，数组[ ]等类型)

 ```xml
 {
	"code": 200,
	"data":T,
	"msg": ""
}
 ```
对应的Repsonse类为
```kotlin
class TKResponse<T>(val code:Int,val msg: String?, val data: T?) : Serializable {
    companion object{
        const val STATUS_OK=200
    }
    fun isSuccess():Boolean{
        return code== STATUS_OK
    }
}
```
* MVC项目

    * 定义MVCHttpCallback,用于将网络请求结果回调给UI界面
    
    
    ```kotlin
   abstract class MVCHttpCallback<T> {

        private val type: Type

        init {
            val arg = TypeUtil.getType(javaClass)
            type = TypeBuilder
                    .newInstance(TKResponse::class.java)
                    .addTypeParam(arg)
                    .build()
        }

        fun getType(): Type {
            return this.type
        }

        /**
         * 请求开始时回调，可以在此加载loading对话框等,默认为空实现
         */
        open fun onStart() {}
       
        /**
         * 抽象方法，请求成功回调，返回内容为泛型，对应TKResponse的data
         */
        abstract fun onSuccess(result: T?)
   
        /**
         * 抽象方法，请求失败回调，返回内容为code(错误码)，msg(错误信息)
         */
        abstract fun onFailure(code: Int, msg: String?)
   
        /**
         * 上传进度回调，默认为空实现
         */
        open fun onProgress(readBytes: Long, totalBytes: Long) {}
   
        /**
         * 请求完成时回调，请求成功之后才会回调此方法，默认为空实现
         */
        open fun onComplete() {}
   
   }
   ```
   
   * 定义网络接口,封装GET/POST等网络请求
   
   
   ```kotlin
   object MVCApi {
   
       /**
        * GET请求
        * context:上下文
        * url：请求url
        * params:参数列表，可为null
        * tag：标识一个网络请求
        * callback：网络请求回调
        */
       inline fun <reified T> httpGet(
               context: Context,
               url: String,
               params: Map<String, Any?>?,
               tag: Any? = null,
               callback: MVCHttpCallback<T>
       ) {
           RxHttp.getInstance().get(context)
                   .url(url)
                   .params(params)
                   .tag(tag)
                   .enqueue(simpleHttpCallback(callback))
       }
   
       /**
        * POST请求
        * context:上下文
        * url：请求url
        * params:参数列表，可为null
        * tag：标识一个网络请求
        * callback：网络请求回调
        */
       inline fun <reified T> httpPost(
               context: Context,
               url: String,
               params: Map<String, Any?>?,
               tag: Any? = null,
               callback: MVCHttpCallback<T>
       ) {
           RxHttp.getInstance().post(context)
                   .url(url)
                   .params(params)
                   .tag(tag)
                   .enqueue(simpleHttpCallback(callback))
       }
       
       ......
       
        inline fun <reified T> simpleHttpCallback(callback: MVCHttpCallback<T>): HttpCallback<TKResponse<T>> {
           return object : HttpCallback<TKResponse<T>>(callback.getType()) {
               override fun onStart() {
                   super.onStart()
                   callback.onStart()
               }
   
               override fun onNext(response: TKResponse<T>?) {
                   if (response != null) {
                       if (response.isSuccess()) {
                           callback.onSuccess(response.data)
                       } else {
                           return onError(ServerException(response.code, response.msg))
                       }
   
                   } else {
                       return onError(NullResponseException(TKErrorCode.ERRCODE_RESPONSE_NULL, TKErrorCode.ERRCODE_RESPONSE_NULL_DESC))
                   }
   
               }
   
               override fun onError(e: Throwable?) {
                   handleThrowable(e).run {
                       callback.onFailure(first, second)
                   }
               }
   
               override fun onComplete() {
                   super.onComplete()
                   callback.onComplete()
               }
   
               override fun onProgress(readBytes: Long, totalBytes: Long) {
                   super.onProgress(readBytes, totalBytes)
                   callback.onProgress(readBytes, totalBytes)
               }
           }
       }
   ```
   
   * 在View层如Activity中调用网络接口
   
   
     ```kotlin
     MVCApi.httpGet(
         context = baseActivity,
         url = TKURL.URL_GET,
         params = null,
         tag = null, 
         callback = object : MVCHttpCallback<List<Banner>>() {
		 override fun onStart() {
		     LogUtils.d(TAG, "onButtonGet onStart() called")
		 }

		 override fun onSuccess(result: List<Banner>?) {
		     Log.d(TAG, "onButtonGet onSuccess() called with: result = $result")
		 }

		 override fun onFailure(code: Int, msg: String?) {
		     Log.d(TAG, "onButtonGet onFailure() called with: code = $code, msg = $msg")
		 }

		 override fun onComplete() {
		     Log.d(TAG, "onButtonGet onComplete() called")
		 }

     })
     ```
   
      **具体使用可以参考demo代码，demo中有详细的示例演示MVC项目如何使用RxHttp**
   
 * MVVM项目
     * 定义Activity基类BaseMvvmActivity
     ```kotlin
	abstract class BaseMvvmActivity<VM : BaseViewModel, VDB : ViewDataBinding> : AppCompatActivity(){

	    lateinit var viewModel: VM
	    lateinit var binding: VDB

	    protected abstract fun getLayoutId(): Int

	    final override fun onCreate(savedInstanceState: Bundle?) {
			onCreateStart(savedInstanceState)
			super.onCreate(savedInstanceState)
			binding = DataBindingUtil.setContentView(this, getLayoutId())
			binding.lifecycleOwner = this
			createViewModel()
			onCreateEnd(savedInstanceState)
	    }

	    protected open fun onCreateStart(savedInstanceState: Bundle?) {}
	    protected open fun onCreateEnd(savedInstanceState: Bundle?) {}

	    /**
	     * 创建ViewModel
	     */
	    private fun createViewModel() {
			val type = findType(javaClass.genericSuperclass)
			val modelClass = if (type is ParameterizedType) {
			    type.actualTypeArguments[0] as Class<VM>
			} else {
			    BaseViewModel::class.java as Class<VM>
			}
			viewModel = ViewModelProvider(this).get(modelClass)
	    }

	    private fun findType(type: Type): Type?{
			return when(type){
			    is ParameterizedType -> type
			    is Class<*> ->{
				findType(type.genericSuperclass)
			    }
			    else ->{
				null
			    }
			}
	    }

	}
     ```
     * 定义ViewModel的基类BaseViewModel
     ```kotlin
	open class BaseViewModel(application: Application) : AndroidViewModel(application) {

	    /**
	     * 网络仓库
	     */
	    protected val networkbaseRepository: NetworkRepository = NetworkRepository.instance
	    
	    /**
	     * 上下文
	     */
	    protected var context: Context = application.applicationContext

	}
     ```
     * 定义网络仓库，封装网络接口
     ```kotlin
	 /**
	 * MVVM架构网络仓库
	 * UI->ViewModel->Repository->LiveData(ViewModel)->UI
	 */
	class NetworkRepository private constructor() {

	    companion object {
			val instance = NetworkRepository.holder
	    }

	    private object NetworkRepository {
			val holder = NetworkRepository()
	    }

	    inline fun <reified T> wrapHttpCallback(): MvvmHttpCallback<T> {
			return object : MvvmHttpCallback<T>() {

			}
	    }

	    inline fun <reified T> newCallback(liveData: MutableLiveData<TKState<T>>): HttpCallback<TKResponse<T>> {
			val type = wrapHttpCallback<T>().getType()
			return object : HttpCallback<TKResponse<T>>(type) {
			    override fun onStart() {
				liveData.value = TKState.start()
			    }

			    override fun onNext(response: TKResponse<T>?) {
				liveData.value = TKState.response(response)
			    }

			    override fun onError(e: Throwable?) {
				liveData.value = TKState.error(e)
			    }

			    override fun onComplete() {

				/**
				 * 亲,此处不要做任何操作,不要给LiveData赋值,防止onNext对应的LiveData数据被覆盖，
				 * 在TKState类handle方法里会特别处理回调的，放心好了
				 */
			    }

			    override fun onProgress(readBytes: Long, totalBytes: Long) {
				liveData.value = TKState.progress(readBytes, totalBytes)
			    }
			}
	    }

	    inline fun <reified T> httpGet(
		    context: Context,
		    url: String,
		    params: Map<String, Any?>?,
		    tag: Any? = null
	    ): MutableLiveData<TKState<T>> {
			val liveData = MutableLiveData<TKState<T>>()
			RxHttp.getInstance()
				.get(context)
				.url(url)
				.params(params)
				.tag(tag)
				.enqueue(newCallback(liveData))
			return liveData
	    }


	    inline fun <reified T> httpPost(
		    context: Context,
		    url: String,
		    params: Map<String, Any?>?,
		    tag: Any? = null
	    ): MutableLiveData<TKState<T>> {
			val liveData = MutableLiveData<TKState<T>>()
			RxHttp.getInstance().post(context)
				.url(url)
				.params(params)
				.tag(tag)
				.enqueue(newCallback(liveData))
			return liveData
	    }

	    inline fun <reified T> httpPostForm(
		    context: Context,
		    url: String,
		    params: Map<String, Any?>?,
		    tag: Any? = null
	    ): MutableLiveData<TKState<T>> {
			val liveData = MutableLiveData<TKState<T>>()
			RxHttp.getInstance().postForm(context)
				.url(url)
				.params(params)
				.tag(tag)
				.enqueue(newCallback(liveData))
			return liveData
	    }

	    inline fun <reified T> httpPut(
		    context: Context,
		    url: String,
		    params: Map<String, Any?>?,
		    tag: Any? = null
	    ): MutableLiveData<TKState<T>> {
			val liveData = MutableLiveData<TKState<T>>()
			RxHttp.getInstance().put(context)
				.url(url)
				.params(params)
				.tag(tag)
				.enqueue(newCallback(liveData))
			return liveData
	    }

	    inline fun <reified T> httpDelete(
		    context: Context,
		    url: String,
		    params: Map<String, Any?>?,
		    tag: Any? = null
	    ): MutableLiveData<TKState<T>> {
			val liveData = MutableLiveData<TKState<T>>()
			RxHttp.getInstance().delete(context)
				.params(params)
				.url(url)
				.tag(tag)
				.enqueue(newCallback(liveData))
			return liveData
	    }


	    /**
	     *上传
	     *支持上传多个文件,map中对应的value类型为File类型或Uri类型
	     *支持监听上传进度
		    val map =Map<String,Any>()
		    map.put("model", "xiaomi")
		    map.put("os", "android")
		    map.put("avatar",File("xxx"))
		    map.put("video",uri)
	     */
	    inline fun <reified T> httpUpload(
		    context: Context,
		    url: String,
		    params: Map<String, Any?>?,
		    tag: Any? = null
	    ): MutableLiveData<TKState<T>> {
			val liveData = MutableLiveData<TKState<T>>()
			RxHttp.getInstance().upload(context)
				.url(url)
				.params(params)
				.tag(tag)
				.enqueue(newCallback(liveData))
			return liveData
	    }


	    /**
	     * 下载
	     * context:上下文,如不需要和生命周期绑定,应该传递applicationContext
	     * url:下载地址
	     * dir:本地目录路径
	     * filename:保存文件名称
	     * callback:下载进度回调
	     * md5:下载文件的MD5值
	     * breakpoint:是否支持断点下载,默认为true
	     */
	    fun httpDownload(context: Context, url: String, dir: String, filename: String, callback: DownloadCallback, md5: String? = null, breakPoint: Boolean = true, tag: Any? = null) {
			RxHttp.getInstance().download(context).dir(dir).filename(filename).breakpoint(breakPoint).md5(md5).url(url).tag(tag).enqueue(callback)
	    }

	}
     ```
     * 经过一系列封装，参考demo代码，最后在View层如Activity中ViewModel调用Repository中的接口
     ```kotlin
      viewModel.testPost(hashMapOf(
                "name" to "jack",
                "location" to "shanghai",
                "age" to 28)
        )
        .observeState(this) {
		    onStart {
			LogUtils.d(TAG, "onButtonPost() onStart called")
		    }
		    onSuccess {
			LogUtils.d(TAG, "onButtonPost() onSuccess called:${it}")
		    }
		    onFailure { code, msg ->
			ToastHelper.toast("onButtonPost() onFailure,code:${code},msg:${msg}")
		    }
		    onComplete {
			LogUtils.d(TAG, "onButtonPost() onComplete called")
		    }
        }
     ```
 
     **具体使用可以参考demo代码，demo中有详细的示例演示MVVM项目如何使用RxHttp**
 
   
# 强烈建议下载Demo代码，Demo中有详细的示例，演示MVVM及MVC架构如何使用RxHttp，如有问题可私信我，[简书](https://www.jianshu.com/u/1b18a5907317) [掘金](https://juejin.cn/user/3808364011199591)

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
