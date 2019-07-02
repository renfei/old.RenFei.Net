package net.renfei.web.controller;

import net.renfei.core.entity.AllInfoDTOList;
import net.renfei.core.service.IndexService;
import net.renfei.web.baseclass.BaseController;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
@RequestMapping("/cat")
public class CatController extends BaseController {
    @Autowired
    private IndexService indexService;

    @RequestMapping("{typeName}/{enName}")
    public ModelAndView getList(
            @PathVariable(value = "typeName") String typeName,
            @PathVariable(value = "enName") String enName,
            @Param(value = "page") String page,
            ModelAndView mv) throws NoHandlerFoundException {
        AllInfoDTOList allInfoDTOList = indexService.getAllInfoByTypeAndCatName(typeName, enName, page);

        if (allInfoDTOList != null &&
                allInfoDTOList.getVAllInfoWithBLOBsList() != null &&
                allInfoDTOList.getVAllInfoWithBLOBsList().size() > 0) {
            //[TODO]
        } else {
            throwNoHandlerFoundException();
        }
        return mv;
    }
}
