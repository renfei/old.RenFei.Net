package net.renfei.web.controller;

import net.renfei.core.entity.PageDTO;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.PageVO;
import net.renfei.web.entity.ShareVO;
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
            ShareVO shareVO = new ShareVO();
            shareVO.setTitle(pageVO.getTitle());
            shareVO.setUrl(domain + "/page/" + pageVO.getId());
            shareVO.setDescribes(pageVO.getDescribes());
            shareVO.setPics("https://cdn.renfei.net/logo/ogimage.png");
            mv.addObject("sharevo", shareVO);
        } else {
            throwNoHandlerFoundException();
        }
        mv.setViewName("pages/page");
        return mv;
    }
}
