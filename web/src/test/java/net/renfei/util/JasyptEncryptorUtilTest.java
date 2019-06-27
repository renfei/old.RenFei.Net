package net.renfei.util;

import lombok.extern.slf4j.Slf4j;
import net.renfei.TestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class JasyptEncryptorUtilTest extends TestApplication {
    @Autowired
    private JasyptEncryptorUtil jasyptEncryptorUtil;

    @Test
    public void encrypt() {
        log.info(jasyptEncryptorUtil.encryptor("jdbc:mysql://localhost/renfei?useSSL=false&serverTimezone=UTC"));
        log.info(jasyptEncryptorUtil.encryptor("root"));
        log.info(jasyptEncryptorUtil.encryptor("root"));
    }
}
