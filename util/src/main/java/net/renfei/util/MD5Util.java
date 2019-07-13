package net.renfei.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Base64;

@Component
public class MD5Util {
    public String encodeByMd5(String string) {
        try {
            // 确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Base64.Encoder base64Encoder = Base64.getEncoder();
            // 加密字符串
            return base64Encoder.encodeToString(md5.digest(string.getBytes("utf-8")));
        } catch (Exception e) {
            return null;
        }

    }
}
