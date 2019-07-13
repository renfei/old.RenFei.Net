package net.renfei.util;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class RSAUtilTest extends TestApplication {
    @Autowired
    private RSAUtils rsaUtils;

    @Test
    public void rsaTest() throws Exception {
        log.info("生成RSA秘钥对");
        Map map = rsaUtils.genKeyPair();
        String publicKey = rsaUtils.getPublicKey(map);
        log.info("获取公钥:" + publicKey);
        String privateKey = rsaUtils.getPrivateKey(map);
        log.info("获取私钥:" + privateKey);
        String plaintext = "This is an RSA asymmetric encryption test";
        log.info("设置明文：" + plaintext);
        log.info("使用私钥加密");
        String data = rsaUtils.encryptedByPrivateKey(plaintext, privateKey);
        log.info("加密后密文：" + data);
        log.info("使用公钥解密");
        String data1 = rsaUtils.decryptByPublicKey(data, publicKey);
        log.info("解密后数据：" + data1);
        Assert.assertEquals(plaintext,data1);
        log.info("使用公钥加密");
        data = rsaUtils.encryptedByPublicKey(plaintext, publicKey);
        log.info("使用私钥解密");
        String data2 = rsaUtils.decryptByPrivateKey(data, privateKey);
        log.info("解密后数据：" + data2);
        Assert.assertEquals(plaintext,data2);
    }
}
