package net.renfei.util;

import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class EccUtils {
    Base64.Decoder decoder = Base64.getDecoder();

    public PublicKey byte2PublicKey(String strPublicKey) throws Exception {
        try {
            KeyFactory factory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decoder.decode(strPublicKey));
            java.security.PublicKey ecPublicKey = factory
                    .generatePublic(x509EncodedKeySpec);
            return ecPublicKey;
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
            throw nsae;
        } catch (InvalidKeySpecException ikse) {
            ikse.printStackTrace();
            throw ikse;
        }
    }

    public PrivateKey byte2PrivateKey(String strPrivateKey) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] byteKey = decoder.decode(strPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(byteKey));
            return privateKey;
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
            throw nsae;
        } catch (InvalidKeySpecException ikse) {
            ikse.printStackTrace();
            throw ikse;
        }
    }
}
