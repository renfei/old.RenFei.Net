package net.renfei.web.controller;

import net.renfei.web.baseclass.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController extends BaseController {
    @Value("classpath:xml/sitemap.xsl")
    private Resource sitemapxslXml;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mv) {
        setHead(mv, "大型主题数字博物馆", "");
        mv.setViewName("index");
        return mv;
    }
}
