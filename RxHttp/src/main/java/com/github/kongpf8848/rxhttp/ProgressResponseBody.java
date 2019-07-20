
package com.github.kongpf8848.rxhttp;

import com.github.kongpf8848.rxhttp.callback.ProgressCallback;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {
    private final ResponseBody responseBody;
    private final ProgressCallback callback;
    private BufferedSource progressSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressCallback callback) {
        this.responseBody = responseBody;
        this.callback = callback;
    }


    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }


    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }


    @Override
    public BufferedSource source() {
        if (callback == null) {
            return responseBody.source();
        }
        if(progressSource==null){
            progressSource=Okio.buffer(source(responseBody.source()));
        }

        return progressSource;
    }

    private Source source(Source source) {

        return new ForwardingSource(source) {

            long totalBytesRead=0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += ((bytesRead != -1) ? bytesRead : 0);
                if(callback!=null) {
                   callback.onProgress(contentLength(),totalBytesRead);
                }
                return bytesRead;
            }
        };
    }

    @Override
    public void close() {
        if (progressSource != null) {
            try {
                progressSource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}