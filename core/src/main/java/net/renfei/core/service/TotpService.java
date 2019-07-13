package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

@Slf4j
@Service
public class TotpService extends BaseService {
    private int SECRET_SIZE = 10;

    private String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

    private int window_size = 5; // default 3 - max 17 (from google docs)最多可偏移的时间

    public void setWindowSize(int s) {
        if (s >= 1 && s <= 17)
            window_size = s;
    }

    /**
     * 验证身份验证码是否正确
     *
     * @param codes       输入的身份验证码
     * @param savedSecret 密钥
     * @return
     */
    public Boolean authcode(String codes, String savedSecret) {
        long code = 0;
        try {
            code = Long.parseLong(codes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        //[TODO]检查是否被使用过，以及记录使用
        long t = System.currentTimeMillis();
        return check_code(savedSecret, code, t);
    }

    /**
     * 获取TOTP串
     *
     * @param userName 用户
     * @param secret   密钥
     * @return TOTP串
     */
    public String genTotpString(String userName, String secret) {
        String format = "otpauth://totp/RenFei.Net:%s?secret=%s&issuer=RenFei.Net";
        return String.format(format, userName, secret);
    }

    public String generateSecretKey() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
            sr.setSeed(Base64.decodeBase64(renFeiConfig.getTotpseed()));
            byte[] buffer = sr.generateSeed(SECRET_SIZE);
            Base32 codec = new Base32();
            byte[] bEncodedKey = codec.encode(buffer);
            String encodedKey = new String(bEncodedKey);
            return encodedKey;
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private boolean check_code(String secret, long code, long timeMsec) {
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);
        long t = (timeMsec / 1000L) / 30L;
        for (int i = -window_size; i <= window_size; ++i) {
            long hash;
            try {
                hash = verify_code(decodedKey, t + i);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            }
            if (hash == code) {
                return true;
            }
        }
        return false;
    }

    private int verify_code(byte[] key, long t)
            throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }
}
