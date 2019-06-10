package net.renfei.www.controller;

import net.renfei.www.common.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/video")
public class VideoController extends BaseController {
    @RequestMapping("play")
    public ModelAndView playVideo(ModelAndView mv) {
        setVideoJS(mv);
        mv.setViewName("video/play");
        return mv;
    }
}
