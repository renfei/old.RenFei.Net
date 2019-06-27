package net.renfei.util;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JasyptEncryptorUtil {
    @Autowired
    StringEncryptor encryptor;

    public String encryptor(String str) {
        return encryptor.encrypt(str);
    }
}
