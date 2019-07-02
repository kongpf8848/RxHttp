# RxHttp

基于Retrofit和RxJava2封装的Http类库，实现了get,post,post表单请求,文件下载及进度监听,文件上传及进度监听功能。

# 功能特点
```
支持上传及进度监听，支持上传多个文件，支持文本和文件混合上传

支持下载及进度监听，支持大文件下载

支持自定义OkHttpClient

链式调用，支持每一个请求动态添加Header

```

# get请求
```
      RxHttp.getInstance()
                .get("your_url")
                .enqueue(new HttpCallback<Feed>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "onStart");
                    }

                    @Override
                    public void onResponse(Feed response) {
                        Toast.makeText(MainActivity.this, response.getDate(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError:" + e.getMessage());
                        Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
```
# post请求
```
  RxHttp.getInstance()
                .post("your_url")
                .content("this is post content")
                .enqueue(new HttpCallback<String>() {

                    @Override
                    public void onStart() {
                        Log.d(TAG, "onStart");
                    }

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "response:" + response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError:" + e.getMessage());
                        Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
```
# post表单请求
```
Map<String, Object> map = new HashMap<>();
        map.put("model", Build.MODEL);
        map.put("manufacturer", Build.MANUFACTURER);
        map.put("os", Build.VERSION.SDK_INT);
        RxHttp.getInstance().postForm("your_url").params(map).enqueue(new HttpCallback<String>() {

            @Override
            public void onStart() {
                Log.d(TAG, "onStart");
            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "response:" + response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError:" + e.getMessage());
                Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
```
# 下载请求
```
RxHttp.getInstance().download(Constants.URL_DOWNLOAD).dir(PATH)
                .enqueue(new DownloadCallback() {

                    @Override
                    public void onStart() {
                        //showProgressDialog("正在下载新版本,请稍等...");
                    }

                    @Override
                    public void onProgress(DownloadInfo downloadInfo) {
                       // if (downloadInfo != null) {
                       //     updateProgress(downloadInfo);
                       // }
                    }

                    @Override
                    public void onResponse(DownloadInfo downloadInfo) {
                       // closeProgressDialog();
                       // MainActivity.this.downloadInfo = downloadInfo;
                       // install();
                    }

                    @Override
                    public void onError(Throwable e) {
                       // closeProgressDialog();

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete:");
                    }

                });

```
# 上传请求
```
Map<String, Object> map = new HashMap<>();
        map.put("model", Build.MODEL);
        map.put("manufacturer", Build.MANUFACTURER);
        map.put("os", Build.VERSION.SDK_INT);
        map.put("image", new File(path));
        RxHttp.getInstance().upload("your_url").params(map).enqueue(new UploadCallback<String>() {
            @Override
            public void onStart() {
                //showProgressDialog("正在上传,请稍等...");
            }

            @Override
            public void onProgress(final long totalBytes, final long readBytes) {
                //updateProgress(uploadInfo);
            }

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "response:" + response);
                //Toast.makeText(MainActivity.this, "response:" + response, Toast.LENGTH_SHORT).show();
                //closeProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                //Log.d(TAG, "onError:" + e.getMessage());
                //Toast.makeText(MainActivity.this, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                //closeProgressDialog();

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
```
