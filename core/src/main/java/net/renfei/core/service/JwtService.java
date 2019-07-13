package net.renfei.core.service;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.TokenSubject;
import net.renfei.util.AesEncryptUtils;
import net.renfei.util.EccUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService extends BaseService {
    @Autowired
    private AesEncryptUtils aesEncryptUtils;
    @Autowired
    private EccUtils eccUtils;

    /**
     * 读取Token
     *
     * @param token
     * @return
     */
    public TokenSubject readToken(String token, String audience) throws Exception {
        TokenSubject tokenSubject = new TokenSubject();
        try {

            PublicKey publicKey = eccUtils.byte2PublicKey(renFeiConfig.getJwtpublickey());
            if (publicKey == null) {
                return null;
            }
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token);
            String au = jws.getBody().getAudience();
            if (au.equals(audience)) {
                String subject = aesEncryptUtils.decrypt(jws.getBody().getSubject(),renFeiConfig.getJwtaeskey());
                return JSON.parseObject(subject, TokenSubject.class);
            } else {
                return null;
            }
        } catch (ExpiredJwtException e) {
        } catch (UnsupportedJwtException e) {
        } catch (MalformedJwtException e) {
        } catch (SignatureException e) {
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return tokenSubject;
    }

    /**
     * 创建Token
     *
     * @param subject  主要载荷
     * @param audience 颁发给谁
     * @return
     * @throws Exception
     */
    public String getToken(TokenSubject subject, String audience) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.ES512;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        PrivateKey privateKey = eccUtils.byte2PrivateKey(renFeiConfig.getJwtprivatekey());
        String id = UUID.randomUUID().toString();
        JwtBuilder builder = Jwts.builder()
                .setIssuer("RenFei.Net API Center")//签发者
                .setSubject(aesEncryptUtils.encrypt(JSON.toJSONString(subject), renFeiConfig.getJwtaeskey()))//主题
                .setAudience(audience)//接收方
                .signWith(privateKey, signatureAlgorithm)
                .setIssuedAt(now) // for example, now
                .setId(id);
        //过期时间
        long expMillis = nowMillis + 7 * 24 * 60 * 60 * 1000;
        Date exp = new Date(expMillis);
        //系统时间之前的token都是不可以被承认的
        builder.setExpiration(exp).setNotBefore(now);
        return builder.compact();
    }

    public boolean validateToken(String token) {
        try {
            PublicKey publicKey = eccUtils.byte2PublicKey(renFeiConfig.getJwtpublickey());
            if (publicKey == null) {
                return false;
            }
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
        } catch (MalformedJwtException ex) {
        } catch (ExpiredJwtException ex) {
        } catch (UnsupportedJwtException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (Exception e) {
        }
        return false;
    }

    public String createKey() {
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.ES512);
        System.out.println(keyPair);
        //记录公钥的x 和y值
        byte[] x = keyPair.getPrivate().getEncoded();
        byte[] y = keyPair.getPublic().getEncoded();
        System.out.println("y:" + new String(y));
        System.out.println("x:" + new String(x));

        System.out.println("si:" + Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));
        System.out.println("go:" + Base64.encodeBase64String(keyPair.getPublic().getEncoded()));
        return Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
    }
}
