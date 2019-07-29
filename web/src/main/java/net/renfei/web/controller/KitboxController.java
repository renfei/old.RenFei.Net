package net.renfei.web.controller;

import net.renfei.web.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/kitbox")
public class KitboxController extends BaseController {
    @RequestMapping("")
    public ModelAndView getKitboxIndex(ModelAndView mv) {
        mv.setViewName("kitbox/index");
        return mv;
    }
}
