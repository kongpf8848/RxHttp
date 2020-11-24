package com.github.kongpf8848.rxhttp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by pengf on 2017/2/21.
 */

public class StreamUtil {

    //将InputStream转换为String
    public static String toString(InputStream is)
    {
        try {
            byte[] byteStream = toByte(is);
            return new String(byteStream, StandardCharsets.UTF_8);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
    //将InputStream转换为字节数组
    public static byte[] toByte(InputStream is) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[]buffer=new byte[1024];
        int len=-1;
        while ((len=is.read(buffer))!=-1)
        {
            baos.write(buffer,0,len);
        }
        baos.flush();
        baos.close();
        is.close();
        return baos.toByteArray();
    }
}
