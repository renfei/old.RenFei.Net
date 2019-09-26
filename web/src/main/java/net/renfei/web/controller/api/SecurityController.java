package net.renfei.web.controller.api;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.entity.SecretKeyDTO;
import net.renfei.core.service.SecretKeyService;
import net.renfei.util.RSAUtils;
import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/security")
public class SecurityController extends BaseRestController {

    /**
     * 获取服务器公钥
     *
     * @return
     */
    @GetMapping("secretkey")
    public APIResult getPublicKey() {
        //生成一个GUID
        String uuid = UUID.randomUUID().toString();
        Map<String, String> data = new HashMap<>();
        data.put("uuid", uuid);
        try {
            //生成一个秘钥对
            Map map = rsaUtils.genKeyPair();
            data.put("publickey", rsaUtils.getPublicKey(map));
            //储存下来
            SecretKeyDTO secretKeyDTO = new SecretKeyDTO();
            secretKeyDTO.setUUID(uuid);
            secretKeyDTO.setServerPrivateKey(rsaUtils.getPrivateKey(map));
            // 获取一年以后的时间
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 1);
            secretKeyDTO.setExpireTime(calendar.getTime());
            secretKeyService.saveSecretKey(secretKeyDTO);
            return APIResult.fillResult(true, uuid, data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return APIResult.fillResult(false, "内部服务器错误");
        }
    }

    /**
     * 存储客户端公钥，返回服务器生成的AES秘钥
     *
     * @param uuid
     * @param publickey
     * @return
     */
    @PostMapping("secretkey/{uuid}")
    public APIResult setPublicKey(@PathVariable("uuid") String uuid, String publickey) {
        SecretKeyDTO secretKeyDTO = secretKeyService.getSecretKeyByUUID(uuid);
        if (secretKeyDTO == null) {
            return APIResult.fillResult(false, "UUID is invalid");
        } else {
            if(stringUtil.isEmpty(secretKeyDTO.getClientPublicKey())) {
                String cPubilicKey = "", aesEncry = "";
                //生成AES秘钥
                String aes = stringUtil.getRandomString(16);
                try {
                    //解密
                    cPubilicKey = rsaUtils.decryptByPrivateKey(publickey, secretKeyDTO.getServerPrivateKey());
                } catch (Exception e) {
                    return APIResult.fillResult(false, "解密失败，请使用服务器颁发的公钥加密你的publickey");
                }
                try {
                    //使用客户端的公钥加密AES秘钥
                    aesEncry = rsaUtils.encryptedByPublicKey(aes, cPubilicKey.replaceAll("\n", ""));
                } catch (Exception e) {
                    return APIResult.fillResult(false, "你的publickey不正确，请生成正确的RSA公钥");
                }
                //存储
                secretKeyDTO.setAesKey(aes);
                secretKeyDTO.setClientPublicKey(cPubilicKey);
                secretKeyService.updateSecretKey(secretKeyDTO);
                Map<String, String> mapData = new HashMap<>();
                mapData.put("aes", aesEncry);
                return APIResult.fillResult(true, "Success!", mapData);
            }else {
                return APIResult.fillResult(false, "The UUID has been used");
            }
        }
    }
}
