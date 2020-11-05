package com.github.kongpf8848.rxhttp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Md5Util {

    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(str.getBytes(StandardCharsets.UTF_8));

            final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);
            for (int i=0; i<bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
                ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
            }
            return ret.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMD5(File file) {
        String hash = null;
        if (file==null || !file.exists()) {
            return hash;
        }
        InputStream is = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, len);
            }
            byte[] bytes = messageDigest.digest();
            hash = byteArray2HexString(bytes);
        } catch (Exception e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hash;
    }

    //字节数组转化为16进制字符串
    public static String byteArray2HexString(byte[] bArr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = bArr.length; i < len; i++) {
            builder.append(String.format("%02X", bArr[i]));
        }
        return builder.toString();
    }

}
