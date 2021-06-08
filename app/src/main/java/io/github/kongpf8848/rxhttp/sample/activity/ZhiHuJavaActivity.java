package io.github.kongpf8848.rxhttp.sample.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.jetbrains.annotations.Nullable;

import io.github.kongpf8848.rxhttp.RxHttp;
import io.github.kongpf8848.rxhttp.callback.HttpCallback;
import io.github.kongpf8848.rxhttp.sample.R;
import io.github.kongpf8848.rxhttp.sample.base.BaseActivity;
import io.github.kongpf8848.rxhttp.sample.bean.Feed;
import io.github.kongpf8848.rxhttp.sample.http.TKURL;

public class ZhiHuJavaActivity extends BaseActivity {
    private static final String TAG = "ZhiHuJavaActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            RxHttp.Companion.getInstance().get(baseActivity).url(TKURL.URL_ZHIHU)
                    .enqueue(new HttpCallback<Feed>() {

                        @Override
                        public void onStart() {
                            super.onStart();
                            Log.d(TAG, "onStart() called");
                        }

                        @Override
                        public void onError(@Nullable Throwable e) {
                            Log.d(TAG, "onError() called with: e = [" + e + "]");
                        }

                        @Override
                        public void onNext(@Nullable Feed response) {
                            Log.d(TAG, "onNext() called with: response = [" + response + "]");
                        }

                        @Override
                        public void onComplete() {
                            super.onComplete();
                            Log.d(TAG, "onComplete() called");
                        }
                    });
        });

    }
}
