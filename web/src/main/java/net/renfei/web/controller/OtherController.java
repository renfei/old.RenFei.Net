package net.renfei.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.renfei.web.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;

@Slf4j
@Controller
@RequestMapping("/other")
public class OtherController extends BaseController {

    /**
     * 链接重定向（注意地址需要Base64编码以后传入）
     *
     * @param url URL需要Base64编码
     * @param mv
     * @return
     */
    @RequestMapping("urlredirect")
    public ModelAndView urlredirect(String url, ModelAndView mv) {
        if (stringUtil.isEmpty(url)) {
            return new ModelAndView("redirect:/");
        }
        try {
            byte[] asBytes = Base64.getDecoder().decode(url);
            mv.addObject("siteName", siteName);
            mv.addObject("url", new String(asBytes, "utf-8"));
            setHead(mv, "提示！Notice! - " + siteName);
            mv.setViewName("other/urlredirect");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ModelAndView("redirect:/");
        }
        return mv;
    }
}
