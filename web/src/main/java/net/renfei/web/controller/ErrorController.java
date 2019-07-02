package net.renfei.web.controller;

import net.renfei.web.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {
    @RequestMapping("404")
    public ModelAndView error404(ModelAndView mv) {
        setHead(mv, "404 Not Found - " + siteName);
        mv.setViewName("error/404");
        return mv;
    }
}
