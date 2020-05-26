package net.renfei.web.controller;

import net.renfei.web.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/services")
public class ServicesController extends BaseController {
    @RequestMapping("")
    public ModelAndView getMavenPage(ModelAndView mv) {
        setHead(mv, "服务列表",
                "属于自己的才是最好的，私有量身定制开发，只有量身打造的才好用。开放编程能力，不仅仅面向普通用户，会开发的你应该会利用别人的能力完成自己的事情。",
                "service,服务,软件,开发");
        setHighlightJS(mv);
        mv.setViewName("services/index");
        return mv;
    }
}
