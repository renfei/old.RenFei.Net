package net.renfei.web.controller;

import net.renfei.core.entity.PageDTO;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
@RequestMapping("/page")
public class PagesController extends BaseController {

    @RequestMapping("{id}")
    public ModelAndView getPage(@PathVariable("id") String id, ModelAndView mv) throws NoHandlerFoundException {
        PageDTO pageDTO = pageService.getPageByID(id);
        if (pageDTO != null) {
            PageVO pageVO = ejbGenerator.convert(pageDTO, PageVO.class);
            mv.addObject("pageVO", pageVO);
            setHead(mv, pageVO.getTitle(), pageVO.getDescribes());
        } else {
            throwNoHandlerFoundException();
        }
        mv.setViewName("pages/page");
        return mv;
    }
}
