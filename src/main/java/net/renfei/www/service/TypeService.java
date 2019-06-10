package net.renfei.www.service;

import net.renfei.www.common.baseclass.BaseService;
import net.renfei.www.dao.TypeDOMapper;
import net.renfei.www.entity.dbo.TypeDO;
import net.renfei.www.entity.dbo.TypeDOExample;
import net.renfei.www.entity.dto.TypeDTO;
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
