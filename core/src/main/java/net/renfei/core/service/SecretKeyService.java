package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.SecretKeyDTO;
import net.renfei.dao.entity.SecretKeyDOExample;
import net.renfei.dao.entity.SecretKeyDOWithBLOBs;
import net.renfei.dao.persistences.SecretKeyDOMapper;
import net.renfei.util.AesEncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SecretKeyService extends BaseService {
    @Autowired
    private SecretKeyDOMapper secretKeyDOMapper;
    @Autowired
    private AesEncryptUtils aesEncryptUtils;

    public int saveSecretKey(SecretKeyDTO secretKeyDTO) {
        return secretKeyDOMapper.insertSelective(ejbGenerator.convert(secretKeyDTO, SecretKeyDOWithBLOBs.class));
    }

    public int updateSecretKey(SecretKeyDTO secretKeyDTO) {
        return secretKeyDOMapper.updateByPrimaryKeyWithBLOBs(ejbGenerator.convert(secretKeyDTO, SecretKeyDOWithBLOBs.class));
    }

    public SecretKeyDTO getSecretKeyByUUID(String uuid) {
        SecretKeyDOWithBLOBs secretKeyDOWithBLOBs = secretKeyDOMapper.selectByUid(uuid);
        if (secretKeyDOWithBLOBs != null) {
            return ejbGenerator.convert(secretKeyDOWithBLOBs, SecretKeyDTO.class);
        }
        return null;
    }

    public String dataDecryption(String data, String uuid) {
        if (!stringUtil.isEmpty(data) && !stringUtil.isEmpty(uuid)) {
            SecretKeyDTO secretKeyDTO = getSecretKeyByUUID(uuid);
            if (secretKeyDTO != null) {
                try {
                    return aesEncryptUtils.decrypt(data, secretKeyDTO.getAesKey());
                } catch (Exception e) {
                    return null;
                }

            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String dataDecryptionByCryptoJS(String data, String uuid) {
        if (!stringUtil.isEmpty(data) && !stringUtil.isEmpty(uuid)) {
            SecretKeyDTO secretKeyDTO = getSecretKeyByUUID(uuid);
            if (secretKeyDTO != null) {
                try {
                    return aesEncryptUtils.decrypt(data, secretKeyDTO.getAesKey(), secretKeyDTO.getAesKey());
                } catch (Exception e) {
                    return null;
                }

            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
