package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.persistences.TypeDOMapper;
import net.renfei.dao.entity.TypeDO;
import net.renfei.dao.entity.TypeDOExample;
import net.renfei.core.entity.TypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService extends BaseService {
    @Autowired
    private TypeDOMapper typeDOMapper;

    public TypeDTO getTypeByID(Long id) {
        return ejbGenerator.convert(typeDOMapper.selectByPrimaryKey(id), TypeDTO.class);
    }

    public List<TypeDTO> getAllType() {
        TypeDOExample typeDOExample = new TypeDOExample();
        typeDOExample.createCriteria();
        return convertTypeDTOS(typeDOMapper.selectByExample(typeDOExample));
    }

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
