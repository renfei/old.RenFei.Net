package net.renfei.www.common.baseclass;

import net.renfei.www.entity.dto.TypeDTO;
import net.renfei.www.entity.vo.FooterVO;
import net.renfei.www.entity.vo.HeaderVO;
import net.renfei.www.entity.vo.PageHeadVO;
import net.renfei.www.service.GlobalService;
import net.renfei.www.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController extends BaseClass {
    protected String siteName;
    protected String domain;
    protected String staticdomain;
    protected PageHeadVO pageHeadVO;
    protected FooterVO footerVO;
    protected HeaderVO headerVO;
    @Autowired
    private GlobalService globalService;
    @Autowired
    private TypeService typeService;

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
     * @param mv
     */
    @ModelAttribute
    public void mdeolAttribute(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
        siteName = globalService.getSiteName();
        domain = globalService.getDomain();
        staticdomain = globalService.getStaticDomain();
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        localRequest.set(request);
        localResponse.set(response);
        //添加全局的css和js文件
        if (pageHeadVO == null) {
            pageHeadVO = new PageHeadVO();
        }
        if (headerVO == null) {
            headerVO = new HeaderVO();
        }
        if (footerVO == null) {
            footerVO = new FooterVO();
        }
        pageHeadVO.setCss(globalService.getGlobalCSSList());
        pageHeadVO.setJss(globalService.getGlobalJSsList());
        pageHeadVO.setScript(globalService.getScript());
        pageHeadVO.setDescription(globalService.getDescription());
        pageHeadVO.setSitename(siteName);
        mv.addObject("head", pageHeadVO);
        //全局页头
        headerVO.setSiteName(siteName);
        headerVO.setDomain(domain);
        mv.addObject("header", headerVO);
        //全局页脚
        footerVO.setAnalyticsCode(globalService.getAnalyticsCode());
        footerVO.setFooterMenu(globalService.getFooterMenu());
        mv.addObject("footer", footerVO);
        mv.addObject("siteName", siteName);
        mv.addObject("domain", domain);
        mv.addObject("staticdomain", staticdomain);
    }

    protected void setHead(ModelAndView mv, String title) {
        setHead(mv, title, "");
    }

    protected void setHead(ModelAndView mv, String title, String description) {
        PageHeadVO pageHeadVO = getHead(mv);
        pageHeadVO.setSitename(title);
        pageHeadVO.setDescription(description);
        mv.addObject("head", pageHeadVO);
    }

    /**
     * 添加代码高亮插件
     *
     * @param mv
     */
    protected void setHighlightJS(ModelAndView mv) {
        PageHeadVO pageHeadVO = getHead(mv);
        List<String> jss = pageHeadVO.getJss();
        jss.add("//" + staticdomain + "/font/highlight/highlight.pack.js");
        pageHeadVO.setJss(jss);
        List<String> css = pageHeadVO.getCss();
        css.add("//" + staticdomain + "/font/highlight/styles/default.css");
        pageHeadVO.setCss(css);
        String script = pageHeadVO.getScript();
        script += "$(function(){$(\"code\").each(function(){$(this).html(\"<ul><li>\" + $(this).html().replace(/\\n/g,\"\\n</li><li>\") +\"\\n</li></ul>\");});});\n" +
                "hljs.initHighlightingOnLoad();";
        pageHeadVO.setScript(script);
        mv.addObject("head", pageHeadVO);
    }

    /**
     * 添加文章页面自己独有的JSCSS
     *
     * @param mv
     */
    protected void setPostsPageJSCSS(ModelAndView mv) {
        PageHeadVO pageHeadVO = getHead(mv);
        List<String> jss = pageHeadVO.getJss();
        jss.add("https://cdn.bootcss.com/tocbot/4.1.1/tocbot.min.js");
        pageHeadVO.setJss(jss);
        List<String> css = pageHeadVO.getCss();
        css.add("//" + staticdomain + "/css/toc.css");
        pageHeadVO.setCss(css);
        mv.addObject("head", pageHeadVO);
    }

    /**
     * 添加视频播放插件
     *
     * @param mv
     */
    protected void setVideoJS(ModelAndView mv) {
        PageHeadVO pageHeadVO = getHead(mv);
//        List<String> jss = pageHeadVO.getJss();
//        jss.add("//" + staticdomain + "/font/plyr/video.min.js");
//        pageHeadVO.setJss(jss);
        List<String> css = pageHeadVO.getCss();
        css.add("//" + staticdomain + "/font/plyr/video.css");
        pageHeadVO.setCss(css);
        String script = pageHeadVO.getScript();
        script += "";
        pageHeadVO.setScript(script);
        mv.addObject("head", pageHeadVO);
    }

    private PageHeadVO getHead(ModelAndView mv) {
        Map<String, Object> map = mv.getModel();
        PageHeadVO pageHeadVO = new PageHeadVO();
        Object obj = map.get("head");
        if (obj instanceof PageHeadVO) {
            pageHeadVO = (PageHeadVO) obj;
        }
        return pageHeadVO;
    }

    protected void throwNoHandlerFoundException() throws NoHandlerFoundException {
        HttpHeaders headers = new HttpHeaders();
        throw new NoHandlerFoundException(localRequest.get().getMethod(), localRequest.get().getRequestURL().toString(), headers);
    }

    protected String getUrl(Long typeId, Long dataId) {
        TypeDTO typeDTO = typeService.getTypeByID(typeId);
        if (typeDTO != null) {
            return domain + typeDTO.getUriPath() + "/" + dataId;
        } else {
            return null;
        }
    }

    protected String geCattUrl(Long typeId, String enName) {
        TypeDTO typeDTO = typeService.getTypeByID(typeId);
        if (typeDTO != null) {
            return domain + typeDTO.getUriPath() + "/cat/" + enName;
        } else {
            return null;
        }
    }
}
