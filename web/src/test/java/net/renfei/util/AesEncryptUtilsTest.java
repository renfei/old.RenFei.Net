package net.renfei.util;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AesEncryptUtilsTest extends TestApplication {
    @Autowired
    private AesEncryptUtils aesEncryptUtils;
    private StringUtil stringUtil = new StringUtil();

    @Test
    public void aesTest() throws Exception {
        String key = stringUtil.getRandomString(16);
        log.info("生成16位的AES秘钥:" + key);
        String plaintext = "这是一个AES加解密测试";
        log.info("设置明文:" + plaintext);
        String data = aesEncryptUtils.encrypt(plaintext, key);
        log.info("加密以后密文：" + data);
        String data1 = aesEncryptUtils.decrypt(data, key);
        log.info("解密以后明文：" + data1);
        Assert.assertEquals(plaintext, data1);
        String data2 = aesEncryptUtils.encrypt(plaintext, key, key);
        log.info("加密以后密文：" + data2);
        String data3 = aesEncryptUtils.decrypt(data2, key, key);
        log.info("解密以后明文：" + data3);
        Assert.assertEquals(plaintext, data3);
    }
}
