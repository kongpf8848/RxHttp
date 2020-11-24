package com.github.kongpf8848.rxhttp;

import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ByteArrayRequestBody extends RequestBody {

    private MediaType contentType;
    private byte[] byteArray;


    public ByteArrayRequestBody(MediaType contentType,byte[]byteArray){
        this.contentType=contentType;
        this.byteArray=byteArray;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return contentType;
    }

    @Override
    public long contentLength() throws IOException {
        return byteArray.length;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.write(byteArray,0,byteArray.length);
    }
}
