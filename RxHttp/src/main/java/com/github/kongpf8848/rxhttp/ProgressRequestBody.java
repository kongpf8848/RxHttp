package com.github.kongpf8848.rxhttp;

import com.github.kongpf8848.rxhttp.callback.ProgressCallback;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;


public class ProgressRequestBody extends RequestBody {
    private final RequestBody requestBody;
    private final ProgressCallback callback;
    private BufferedSink progressSink;


    public ProgressRequestBody(RequestBody requestBody, ProgressCallback callback) {
        this.requestBody = requestBody;
        this.callback = callback;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (callback == null) {
            requestBody.writeTo(sink);
            return;
        }
        if (progressSink == null) {
            progressSink = Okio.buffer(sink(sink));
        }
        callback.onProgress(0,contentLength());
        requestBody.writeTo(progressSink);
        progressSink.flush();
        progressSink.close();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            long totalBytesWrite = 0L;
            long lastBytesWrite=0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                lastBytesWrite=totalBytesWrite;
                totalBytesWrite += byteCount;
                if(totalBytesWrite!=lastBytesWrite) {
                    if (callback != null) {
                        callback.onProgress(totalBytesWrite, contentLength());
                    }
                }
            }
        };
    }
}