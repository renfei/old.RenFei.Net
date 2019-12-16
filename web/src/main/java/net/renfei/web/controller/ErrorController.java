package net.renfei.web.controller;

import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.PageHeadVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {
    @RequestMapping("404")
    public ModelAndView error404(ModelAndView mv) {
        Map<String, Object> map = mv.getModel();
        PageHeadVO pageHeadVO = null;
        Object obj = map.get(HEAD_KEY);
        if (obj instanceof PageHeadVO) {
            pageHeadVO = (PageHeadVO) obj;
        }
        if (pageHeadVO != null) {
            List<String> jss = pageHeadVO.getJss();
            jss.add("//qzonestyle.gtimg.cn/qzone/v6/portal/gy/404/data.js");
            pageHeadVO.setJss(jss);
            mv.addObject(HEAD_KEY,pageHeadVO);
        }
        setHead(mv, "404 Not Found");
        mv.setViewName("error/404");
        return mv;
    }
}
