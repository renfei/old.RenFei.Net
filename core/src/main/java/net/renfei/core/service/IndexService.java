package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.AllInfoDTOList;
import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.entity.PostsListDTO;
import net.renfei.core.entity.TypeDTO;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.entity.VAllInfoExample;
import net.renfei.dao.entity.VAllInfoWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "IndexService")
public class IndexService extends BaseService {
    @Autowired
    private TypeService typeService;
    @Autowired
    private CategorService categorService;
    @Autowired
    private PostsService postsService;

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1", condition = "#p0!=null&&#p1!=null")
    public AllInfoDTOList getAllInfo(String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        AllInfoDTOList allInfoDTOList;
        VAllInfoExample vAllInfoExample = new VAllInfoExample();
        vAllInfoExample.setOrderByClause("release_time DESC");
        vAllInfoExample.createCriteria();
        Page page = PageHelper.startPage(intPage, intRows);
        List<VAllInfoWithBLOBs> vAllInfoWithBLOBsList = vAllInfoMapper.selectByExampleWithBLOBs(vAllInfoExample);
        allInfoDTOList = new AllInfoDTOList();
        allInfoDTOList.setCount(page.getTotal());
        allInfoDTOList.setVAllInfoWithBLOBsList(vAllInfoWithBLOBsList);
        return allInfoDTOList;
    }

    public AllInfoDTOList getAllInfoByTypeAndCatName(String typeName, String enName, String page) {
        return getAllInfoByTypeAndCatName(typeName, enName, page, "10");
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1+'_'+#p2+'_'+#p3", condition = "#p0!=null&&#p1!=null&&#p2!=null&&#p3!=null")
    public AllInfoDTOList getAllInfoByTypeAndCatName(String typeName, String enName, String pages, String rows) {
        List<TypeDTO> typeDTOS = typeService.getTypeByName(typeName);
        if (typeDTOS != null && typeDTOS.size() > 0) {
            List<CategoryDTO> categoryDTOS = categorService.getCatByTypeIdAndName(typeDTOS.get(0).getId(), enName);
            if (categoryDTOS != null && categoryDTOS.size() > 0) {
                //[TODO]查询该分类下的内容列表
                AllInfoDTOList allInfoDTOList = null;
                switch (typeDTOS.get(0).getTypeName()) {
                    case "Posts":
                        allInfoDTOList = convert(postsService.getAllPostsByCatID(categoryDTOS.get(0).getId(), pages, rows));
                        break;
                    case "Pages":
                        break;
                    case "Video":
                        break;
                    case "Photo":
                        break;
                    default:
                        return null;
                }
                return allInfoDTOList;
            }
        }
        return null;
    }

    private AllInfoDTOList convert(PostsListDTO postsListDTO) {
        if (postsListDTO != null && postsListDTO.getCount() > 0) {
            AllInfoDTOList allInfoDTOList = new AllInfoDTOList();
            List<VAllInfoWithBLOBs> vAllInfoWithBLOBs = new ArrayList<>();
            for (PostsDOWithBLOBs obj : postsListDTO.getPostsList()
            ) {
                VAllInfoWithBLOBs v = new VAllInfoWithBLOBs();
                v.setTitle(obj.getTitle());
                v.setDescribes(obj.getDescribes());
                v.setFeaturedImage(obj.getFeaturedImage());
                vAllInfoWithBLOBs.add(v);
            }
            allInfoDTOList.setVAllInfoWithBLOBsList(vAllInfoWithBLOBs);
            allInfoDTOList.setCount(postsListDTO.getCount());
            return allInfoDTOList;
        } else {
            return null;
        }
    }

    @Override
    public int convertPage(String page) {
        return super.convertPage(page);
    }
}
