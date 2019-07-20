package com.github.kongpf8848.rxhttp.converter;

import com.github.kongpf8848.rxhttp.Platform;
import com.github.kongpf8848.rxhttp.ProgressResponseBody;
import com.github.kongpf8848.rxhttp.bean.DownloadInfo;
import com.github.kongpf8848.rxhttp.callback.DownloadCallback;
import com.github.kongpf8848.rxhttp.callback.ProgressCallback;
import com.github.kongpf8848.rxhttp.exception.HttpError;
import com.github.kongpf8848.rxhttp.util.LogUtil;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

public class DownloadConverter<T> implements IConverter<T> {

    private DownloadInfo downloadInfo;
    private DownloadCallback callback;
    public DownloadConverter(DownloadInfo downloadInfo,DownloadCallback callback) {
        this.downloadInfo = downloadInfo;
        this.callback=callback;
    }

    @Override
    public T convert(final ResponseBody body,Type type) throws Exception {
        try {
            File fileDir = new File(downloadInfo.getDestDir());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File file = new File(downloadInfo.getDestDir(), downloadInfo.getFileName());
            if (file.exists()) {
                file.delete();
            }
            LogUtil.d("++++++++++++content length:" + body.contentLength());
            ProgressResponseBody progressResponseBody = new ProgressResponseBody(body, new ProgressCallback() {
                @Override
                public void onProgress(long totalBytes, long readBytes) {
                    LogUtil.d("total:" + totalBytes + ",read:" + readBytes);
                    downloadInfo.setProgress(readBytes);
                    Platform.get().defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onProgress(downloadInfo);
                        }
                    });
                }
            });
            downloadInfo.setTotal(progressResponseBody.contentLength());
            Platform.get().defaultCallbackExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.d("onProgress");
                    callback.onProgress(downloadInfo);
                }
            });

            BufferedSink sink = Okio.buffer(Okio.sink(file));
            sink.writeAll(progressResponseBody.source());
            sink.flush();
            sink.close();
            progressResponseBody.close();

        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            throw new HttpError(HttpError.CODE_DOWNLOAD_EXCEPTION, e.getMessage());
        }
        return (T)downloadInfo;

    }
}
