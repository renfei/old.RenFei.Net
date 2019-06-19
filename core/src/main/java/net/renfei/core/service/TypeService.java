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
        List<TypeDO> typeDOList = typeDOMapper.selectByExample(typeDOExample);
        if (typeDOList != null && typeDOList.size() > 0) {
            List<TypeDTO> typeDTOS = new ArrayList<>();
            for (TypeDO t : typeDOList
            ) {
                typeDTOS.add(ejbGenerator.convert(t, TypeDTO.class));
            }
            return typeDTOS;
        } else {
            return null;
        }
    }
}
