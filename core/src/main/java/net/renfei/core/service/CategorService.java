package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.entity.TypeDTO;
import net.renfei.dao.entity.CategoryDO;
import net.renfei.dao.entity.CategoryDOExample;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "CategorService")
public class CategorService extends BaseService {
    @Autowired
    private TypeService typeService;

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public CategoryDTO getCategoryByID(Long id) {
        CategoryDTO categoryDTO = ejbGenerator.convert(categoryDOMapper.selectByPrimaryKey(id), CategoryDTO.class);
        TypeDTO typeDTO = typeService.getTypeByID(categoryDTO.getTypeId());
        categoryDTO.setTypeName(typeDTO.getTypeName());
        categoryDTO.setUriPath(typeDTO.getUriPath());
        return categoryDTO;
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public CategoryDTO getCategoryByEnNaeme(String enName) {
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria()
                .andEnNameEqualTo(enName);
        List<CategoryDO> categoryDOS = categoryDOMapper.selectByExampleWithBLOBs(categoryDOExample);
        if (categoryDOS != null && categoryDOS.size() > 0) {
            CategoryDTO categoryDTO = ejbGenerator.convert(categoryDOS.get(0), CategoryDTO.class);
            TypeDTO typeDTO = typeService.getTypeByID(categoryDTO.getTypeId());
            categoryDTO.setTypeName(typeDTO.getTypeName());
            categoryDTO.setUriPath(typeDTO.getUriPath());
            return categoryDTO;
        } else {
            return null;
        }

    }

    public CategoryDTO getCatByPost(PostsDOWithBLOBs postsListDTO) {
        return getCategoryByID(postsListDTO.getCategoryId());
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public List<CategoryDTO> getAllCategoryByType(Long id) {
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria().andTypeIdEqualTo(id);
        List<CategoryDO> categoryDOS = categoryDOMapper.selectByExampleWithBLOBs(categoryDOExample);
        if (categoryDOS != null && categoryDOS.size() > 0) {
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            TypeDTO typeDTO = typeService.getTypeByID(id);
            for (CategoryDO cdo : categoryDOS
            ) {
                CategoryDTO categoryDTO = ejbGenerator.convert(cdo, CategoryDTO.class);
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

    @Cacheable(key = "targetClass+'_'+methodName")
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

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1", condition = "#p0!=null&&#p1!=null")
    public List<CategoryDTO> getCatByTypeIdAndName(Long TypeID, String name) {
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria()
                .andTypeIdEqualTo(TypeID)
                .andEnNameEqualTo(name);
        return ejbGenerator.convert(categoryDOMapper.selectByExampleWithBLOBs(categoryDOExample), CategoryDTO.class);
    }
}
