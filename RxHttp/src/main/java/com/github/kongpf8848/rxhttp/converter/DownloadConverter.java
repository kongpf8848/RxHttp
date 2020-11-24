package com.github.kongpf8848.rxhttp.converter;

import com.github.kongpf8848.rxhttp.Platform;
import com.github.kongpf8848.rxhttp.ProgressResponseBody;
import com.github.kongpf8848.rxhttp.bean.DownloadInfo;
import com.github.kongpf8848.rxhttp.callback.DownloadCallback;
import com.github.kongpf8848.rxhttp.callback.ProgressCallback;
import com.github.kongpf8848.rxhttp.request.DownloadRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

public class DownloadConverter<T> implements IConverter<T> {

    private DownloadInfo downloadInfo;
    private DownloadCallback callback;
    private DownloadRequest downloadRequest;
    public DownloadConverter(DownloadRequest downloadRequest,DownloadCallback callback) {
        this.downloadRequest = downloadRequest;
        this.callback=callback;
        this.downloadInfo=new DownloadInfo(this.downloadRequest.getUrl(),this.downloadRequest.getDir(),this.downloadRequest.getFilename());
    }

    @Override
    public T convert(final ResponseBody body,Type type) throws Exception {
        try {
            File fileDir = new File(downloadInfo.getDestDir());
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File file=new File(downloadInfo.getDestDir(),downloadInfo.getFileName());
            final long currentProgress;
            if(downloadRequest.isBreakpoint()){
                if(file.exists()){
                    currentProgress=file.length();
                }
                else{
                    currentProgress=0;
                }
            }
            else{
                currentProgress=0;
            }
            FileOutputStream fos=new FileOutputStream(file,downloadRequest.isBreakpoint());
            ProgressResponseBody progressResponseBody = new ProgressResponseBody(body, new ProgressCallback() {
                @Override
                public void onProgress(long readBytes,long totalBytes) {
                    downloadInfo.setProgress(currentProgress+readBytes);
                    Platform.get().defaultCallbackExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onProgress(downloadInfo);
                        }
                    });
                }
            });
            downloadInfo.setTotal(currentProgress+progressResponseBody.contentLength());
            Platform.get().defaultCallbackExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    callback.onProgress(downloadInfo);
                }
            });

            BufferedSink sink = Okio.buffer(Okio.sink(fos));
            sink.writeAll(progressResponseBody.source());
            sink.flush();
            sink.close();
            progressResponseBody.close();

        } catch (Exception e) {
            throw e;
        }
        return (T)downloadInfo;

    }
}
