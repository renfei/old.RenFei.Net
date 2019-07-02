package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.entity.PostsListDTO;
import net.renfei.core.entity.TypeDTO;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.persistences.VAllInfoMapper;
import net.renfei.dao.entity.VAllInfoExample;
import net.renfei.dao.entity.VAllInfoWithBLOBs;
import net.renfei.core.entity.AllInfoDTOList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService extends BaseService {
    @Autowired
    private VAllInfoMapper vAllInfoMapper;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CategorService categorService;
    @Autowired
    private PostsService postsService;

    public AllInfoDTOList getAllInfo(String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        VAllInfoExample vAllInfoExample = new VAllInfoExample();
        vAllInfoExample.setOrderByClause("release_time DESC");
        vAllInfoExample.createCriteria();
        Page page = PageHelper.startPage(intPage, intRows);
        List<VAllInfoWithBLOBs> vAllInfoWithBLOBsList = vAllInfoMapper.selectByExampleWithBLOBs(vAllInfoExample);
        AllInfoDTOList allInfoDTOList = new AllInfoDTOList();
        allInfoDTOList.setCount(page.getTotal());
        allInfoDTOList.setVAllInfoWithBLOBsList(vAllInfoWithBLOBsList);
        return allInfoDTOList;
    }

    public AllInfoDTOList getAllInfoByTypeAndCatName(String typeName, String enName, String page) {
        return getAllInfoByTypeAndCatName(typeName, enName, page, "10");
    }

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
}
