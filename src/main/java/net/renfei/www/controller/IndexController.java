package net.renfei.www.controller;

import net.renfei.www.common.baseclass.BaseController;
import net.renfei.www.entity.dbo.VAllInfoWithBLOBs;
import net.renfei.www.entity.dto.AllInfoDTOList;
import net.renfei.www.entity.dto.TypeDTO;
import net.renfei.www.entity.vo.AllInfoVO;
import net.renfei.www.service.IndexService;
import net.renfei.www.service.TypeService;
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
        mv.setViewName("index");
        return mv;
    }
}
