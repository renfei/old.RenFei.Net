package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.entity.DownloadDO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "DownloadService")
public class DownloadService extends BaseService {
    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public DownloadDO getDownloadInfoById(String id) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                return downloadDOMapper.selectByPrimaryKey(ID);
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }
}
