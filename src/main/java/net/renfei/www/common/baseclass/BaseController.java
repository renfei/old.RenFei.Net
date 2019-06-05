package net.renfei.www.common.baseclass;

import net.renfei.www.entity.vo.FooterVO;
import net.renfei.www.entity.vo.PageHeadVO;
import net.renfei.www.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController extends BaseClass {
    @Autowired
    protected PageHeadVO pageHeadVO;
    @Autowired
    protected FooterVO footerVO;
    @Autowired
    private GlobalService globalService;

    /**
     * 线程绑定Request对象
     */
    protected ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<>();

    /**
     * 线程绑定Response对象
     */
    protected ThreadLocal<HttpServletResponse> localResponse = new ThreadLocal<>();

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param request
     * @param response
     * @param model
     */
    @ModelAttribute
    public void mdeolAttribute(HttpServletRequest request, HttpServletResponse response, Model model) {
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        localRequest.set(request);
        localResponse.set(response);
        //添加全局的css和js文件
        pageHeadVO.setCss(globalService.getGlobalCSSList());
        pageHeadVO.setJss(globalService.getGlobalJSsList());
        pageHeadVO.setScript("");
        pageHeadVO.setSitename(globalService.getSiteName());
        footerVO.setAnalyticsCode("<!-- Global site tag (gtag.js) - Google Analytics -->\n" +
                "            <script async src=\"https://www.googletagmanager.com/gtag/js?id=UA-141370164-1\"></script>\n" +
                "            <script>\n" +
                "                window.dataLayer = window.dataLayer || [];\n" +
                "                function gtag(){dataLayer.push(arguments);}\n" +
                "                gtag('js', new Date());\n" +
                "\n" +
                "                gtag('config', 'UA-141370164-1');\n" +
                "            </script>");
        model.addAttribute("head", pageHeadVO);
        //全局页脚
        List<Map> map = new ArrayList<>();
        Map<String, String> m = new HashMap<>();
        m.put("link", "#");
        m.put("text", "测试");
        for (int i = 0; i < 5; i++) {
            map.add(m);
        }
        footerVO.setFooterMenu(map);
        model.addAttribute("footer", footerVO);
    }
}
