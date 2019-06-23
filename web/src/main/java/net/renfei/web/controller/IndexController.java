package net.renfei.web.controller;

import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.service.CategorService;
import net.renfei.web.baseclass.BaseController;
import net.renfei.dao.entity.VAllInfoWithBLOBs;
import net.renfei.core.entity.AllInfoDTOList;
import net.renfei.core.entity.TypeDTO;
import net.renfei.web.entity.AllInfoVO;
import net.renfei.core.service.IndexService;
import net.renfei.core.service.TypeService;
import net.renfei.web.entity.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController extends BaseController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CategorService categorService;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mv,
                              @RequestParam(value = "page", required = false) String page,
                              @RequestParam(value = "rows", required = false) String rows) {
        AllInfoDTOList allInfoDTOList = indexService.getAllInfo(page, rows);
        if (allInfoDTOList.getCount() > 0) {
            List<AllInfoVO> allInfoVOList = new ArrayList<>();
            for (VAllInfoWithBLOBs vAllInfoWithBLOBs : allInfoDTOList.getVAllInfoWithBLOBsList()
            ) {
                AllInfoVO obj = ejbGenerator.convert(vAllInfoWithBLOBs, AllInfoVO.class);
                obj.setHref(getUrl(obj.getTypeId(), obj.getId()));
                obj.setCatHref(geCattUrl(obj.getTypeId(), obj.getCatEname()));
                allInfoVOList.add(obj);
            }
            mv.addObject("allInfoVOList", allInfoVOList);
            mv.addObject("count", allInfoDTOList.getCount());
        }
        List<TypeDTO> typeDTOS = typeService.getAllType();
        mv.addObject("typeDTOS", typeDTOS);
        List<CategoryDTO> categoryDTOS = categorService.getAllCategory();
        if (categoryDTOS != null && categoryDTOS.size() > 0) {
            List<CategoryVO> categoryVOS = new ArrayList<>();
            for (CategoryDTO cat : categoryDTOS
            ) {
                CategoryVO categoryVO = ejbGenerator.convert(cat, CategoryVO.class);
                categoryVO.setHref(domain + categoryVO.getUriPath() + "/" + categoryVO.getEnName());
                categoryVOS.add(categoryVO);
            }
            mv.addObject("categories", categoryVOS);
        }
        mv.addObject("homebanner", globalService.getHomeBanner());
        mv.setViewName("index");
        return mv;
    }
}
