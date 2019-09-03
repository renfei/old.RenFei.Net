package net.renfei.web.controller;

import net.renfei.web.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/maven")
public class MavenController extends BaseController {
    @RequestMapping("")
    public ModelAndView getMavenPage(ModelAndView mv) {
        setHead(mv, "Maven Agent Repository", "Maven公共代理仓库，助力开发者快速构建自己的项目。代理常见Maven仓库源，有效解决国内仓库速度慢、仓库包丢失的问题。");
        setHighlightJS(mv);
        mv.setViewName("maven/index");
        return mv;
    }
}
