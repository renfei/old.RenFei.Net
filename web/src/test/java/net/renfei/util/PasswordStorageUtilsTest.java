package net.renfei.util;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PasswordStorageUtilsTest extends TestApplication {
    @Autowired
    private PasswordStorageUtils passwordStorageUtils;

    @Test
    public void Test() throws Exception {
        log.info("Test PasswordStorageUtils Start.");
        String password = "renfei";
        String hashPasswd = passwordStorageUtils.createHash(password);
        log.info(hashPasswd);
        //断言
        Assert.assertTrue(passwordStorageUtils.verifyPassword(password, hashPasswd));
        log.info("Test PasswordStorageUtils End.");
    }
}
