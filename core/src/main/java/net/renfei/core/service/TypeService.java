package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.TypeDTO;
import net.renfei.dao.entity.TypeDO;
import net.renfei.dao.entity.TypeDOExample;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "TypeService")
public class TypeService extends BaseService {

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public TypeDTO getTypeByID(Long id) {
        return ejbGenerator.convert(typeDOMapper.selectByPrimaryKey(id), TypeDTO.class);
    }

    @Cacheable(key = "targetClass+'_'+methodName")
    public List<TypeDTO> getAllType() {
        TypeDOExample typeDOExample = new TypeDOExample();
        typeDOExample.createCriteria();
        return convertTypeDTOS(typeDOMapper.selectByExample(typeDOExample));
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public List<TypeDTO> getTypeByName(String name) {
        TypeDOExample typeDOExample = new TypeDOExample();
        typeDOExample.createCriteria().andTypeNameEqualTo(name);
        return convertTypeDTOS(typeDOMapper.selectByExample(typeDOExample));
    }

    private List<TypeDTO> convertTypeDTOS(List<TypeDO> typeDOS) {
        if (typeDOS != null && typeDOS.size() > 0) {
            return ejbGenerator.convert(typeDOS, TypeDTO.class);
        } else {
            return null;
        }
    }
}
