package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.entity.TypeDTO;
import net.renfei.dao.entity.CategoryDO;
import net.renfei.dao.entity.CategoryDOExample;
import net.renfei.dao.persistences.CategoryDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorService extends BaseService {
    @Autowired
    private CategoryDOMapper categoryDOMapper;
    @Autowired
    private TypeService typeService;

    public List<CategoryDTO> getAllCategory() {
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria();
        List<CategoryDO> categoryDOS = categoryDOMapper.selectByExampleWithBLOBs(categoryDOExample);
        if (categoryDOS != null && categoryDOS.size() > 0) {
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (CategoryDO cdo : categoryDOS
            ) {
                CategoryDTO categoryDTO = ejbGenerator.convert(cdo, CategoryDTO.class);
                TypeDTO typeDTO = typeService.getTypeByID(categoryDTO.getTypeId());
                if (typeDTO != null) {
                    categoryDTO.setTypeName(typeDTO.getTypeName());
                    categoryDTO.setUriPath(typeDTO.getUriPath());
                }
                categoryDTOS.add(categoryDTO);
            }
            return categoryDTOS;
        } else {
            return null;
        }
    }
}
