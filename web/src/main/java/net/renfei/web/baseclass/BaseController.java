package net.renfei.web.baseclass;

import net.renfei.core.baseclass.BaseClass;
import net.renfei.core.entity.*;
import net.renfei.core.service.*;
import net.renfei.web.entity.*;
import net.renfei.web.service.PaginationService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController extends BaseClass {
    protected static final String HEAD_KEY = "head";
    protected static final String HEADER_KEY = "header";
    protected static final String FOOTER_KEY = "footer";
    protected static final String SIDEBAR_KEY = "sidebar";
    protected static final String SITE_NAME_KEY = "siteName";
    protected static final String SITE_LOGO_KEY = "sitelogo";
    protected static final String DOMAIN_KEY = "domain";
    protected static final String STATIC_DOMAIN_KEY = "staticdomain";
    protected static final String GLOBAL_COMMENT = "globalComment";
    protected String siteName;
    protected String siteLogo;
    protected String domain;
    protected String staticdomain;
    protected String globalComment;
    protected PageHeadVO pageHeadVO;
    protected FooterVO footerVO;
    protected HeaderVO headerVO;
    protected SidebarVO sidebarVO;
    @Autowired
    protected GlobalService globalService;
    @Autowired
    protected TypeService typeService;
    @Autowired
    protected MenuService menuService;
    @Autowired
    protected PaginationService paginationService;
    @Autowired
    protected CommentsService commentsService;
    @Autowired
    protected VideoService videoService;
    @Autowired
    protected PostsService postsService;
    @Autowired
    protected CategorService categorService;
    @Autowired
    protected PhotoService photoService;
    @Autowired
    protected IndexService indexService;
    @Autowired
    protected PageService pageService;
    @Autowired
    protected SearchService searchService;
    @Autowired
    protected LinkService linkService;
    @Autowired
    protected SiteMapService siteMapService;
    @Autowired
    protected IpService ipService;
    @Autowired
    protected MailService mailService;
    @Autowired
    protected LibraryService libraryService;
    @Autowired
    protected MoviesService moviesService;

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
        siteLogo = globalService.getSiteLogo();
        domain = globalService.getDomain();
        staticdomain = globalService.getStaticDomain();
        globalComment = globalService.getGlobalComment();
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
        mv.addObject(HEAD_KEY, pageHeadVO);
        //全局页头
        headerVO.setSiteName(siteName);
        headerVO.setSiteLogo(siteLogo);
        headerVO.setDomain(domain);
        //添加全局菜单
        List<MenuVO> menuVOS = ejbGenerator.convert(menuService.getAllHeadMenu(), MenuVO.class);
        List<MenuVO> topNavVOS = ejbGenerator.convert(menuService.getTopNavMenu(), MenuVO.class);
        headerVO.setMenuVOS(menuVOS);
        headerVO.setTopNavVOS(topNavVOS);
        mv.addObject(HEADER_KEY, headerVO);
        //全局页脚
        footerVO.setLogo(globalService.getSiteLogo());
        footerVO.setAnalyticsCode(globalService.getAnalyticsCode());
        footerVO.setFooterMenu(ejbGenerator.convert(menuService.getAllFooterMenu(), MenuVO.class));
        footerVO.setCopyList(ejbGenerator.convert(menuService.getAllFooterCopyMenu(), MenuVO.class));
        //全局侧边栏
        sidebarVO = new SidebarVO();
        mv.addObject(FOOTER_KEY, footerVO);
        mv.addObject(SIDEBAR_KEY, sidebarVO);
        mv.addObject(SITE_NAME_KEY, siteName);
        mv.addObject(DOMAIN_KEY, domain);
        mv.addObject(STATIC_DOMAIN_KEY, staticdomain);
        mv.addObject(GLOBAL_COMMENT, globalComment);
    }

    protected void setHead(ModelAndView mv, String title) {
        setHead(mv, title, "");
    }

    protected void setHead(ModelAndView mv, String title, String description) {
        PageHeadVO pageHeadVO = getHead(mv);
        pageHeadVO.setSitename(title + " - " + siteName);
        pageHeadVO.setDescription(description);
        mv.addObject(HEAD_KEY, pageHeadVO);
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
        css.add("//" + staticdomain + "/font/highlight/styles/idea.css");
        pageHeadVO.setCss(css);
        String script = pageHeadVO.getScript();
        script += "$(function(){$(\"code\").each(function(){$(this).html(\"<ol><li>\" + $(this).html().replace(/\\n/g,\"\\n</li><li>\") +\"\\n</li></ol>\");});});\n" +
                "hljs.initHighlightingOnLoad();";
        pageHeadVO.setScript(script);
        mv.addObject(HEAD_KEY, pageHeadVO);
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
        mv.addObject(HEAD_KEY, pageHeadVO);
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
        mv.addObject(HEAD_KEY, pageHeadVO);
    }

    private PageHeadVO getHead(ModelAndView mv) {
        Map<String, Object> map = mv.getModel();
        PageHeadVO pageHeadVO = new PageHeadVO();
        Object obj = map.get(HEAD_KEY);
        if (obj instanceof PageHeadVO) {
            pageHeadVO = (PageHeadVO) obj;
        }
        return pageHeadVO;
    }

    protected Object getObjFromMV(ModelAndView mv, String key) {
        Map<String, Object> map = mv.getModel();
        return map.get(key);
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

    protected void setComment(ModelAndView mv, Long typeid, Long id) {
        List<CommentVO> commentVOS = new ArrayList<>();
        List<CommentDTO> commentDTOS = commentsService.getComment(typeid, id);
        if (commentDTOS != null) {
            for (CommentDTO dto : commentDTOS
            ) {
                CommentVO commentVO = new CommentVO();
                commentVO = commentVO.convert(dto);
                commentVOS.add(commentVO);
            }
        }
        mv.addObject("commentTypeId", typeid);
        mv.addObject("commentObjId", id);
        mv.addObject("commentVO", commentVOS);
    }

    protected void setPagination(ModelAndView mv, String page, Long count, String link) {
        int totalPage = Integer.valueOf((count / 10) + "");
        if (count % 10 > 0) {
            totalPage++;
        }
        if (totalPage <= 0) {
            totalPage = 1;
        }
        mv.addObject("pagination", paginationService.getPagination(page, totalPage, domain + link));
    }

    /**
     * 文章的侧边栏
     *
     * @param mv
     */
    protected void setSidebarByPost(ModelAndView mv, String id) {
        SidebarVO sidebarVO = new SidebarVO();
        List<CategoryDTO> categoryDTOS = categorService.getAllCategoryByType(1L);
        PostsDTO postsDTO = postsService.getPostsByID(id, false);
        if (categoryDTOS != null && categoryDTOS.size() > 0) {
            List<CategoriesVo> categories = new ArrayList<>();
            for (CategoryDTO c : categoryDTOS
            ) {
                CategoriesVo categoriesVo = new CategoriesVo();
                categoriesVo.setCategoriesName(c.getZhName());
                categoriesVo.setCnt(postsService.getCountByCategoryId(c.getId()));
                categoriesVo.setLink(domain + "/cat/Posts/" + c.getEnName());
                if (postsDTO != null && c.getId().equals(postsDTO.getCategoryId())) {
                    categoriesVo.setActiv(true);
                } else {
                    categoriesVo.setActiv(false);
                }
                categories.add(categoriesVo);
            }
            sidebarVO.setCategories(categories);
        }
        //[TODO]标签类
        sidebarVO.setStaticdomain(staticdomain);
        mv.addObject(SIDEBAR_KEY, sidebarVO);
    }

    /**
     * 视频的侧边栏
     *
     * @param mv
     */
    protected void setSidebarByVideo(ModelAndView mv, String id) {
        SidebarVO sidebarVO = new SidebarVO();
        List<CategoryDTO> categoryDTOS = categorService.getAllCategoryByType(3L);
        VideoDTO videoDTO = videoService.getVideoByID(id);
        if (categoryDTOS != null && categoryDTOS.size() > 0) {
            List<CategoriesVo> categories = new ArrayList<>();
            for (CategoryDTO c : categoryDTOS
            ) {
                CategoriesVo categoriesVo = new CategoriesVo();
                categoriesVo.setCategoriesName(c.getZhName());
                categoriesVo.setCnt(videoService.getCountByCategoryId(c.getId()));
                categoriesVo.setLink(domain + "/cat/Video/" + c.getEnName());
                if (videoDTO != null && c.getId().equals(videoDTO.getCategoryId())) {
                    categoriesVo.setActiv(true);
                } else {
                    categoriesVo.setActiv(false);
                }
                categories.add(categoriesVo);
            }
            sidebarVO.setCategories(categories);
        }
        //[TODO]标签类
        sidebarVO.setStaticdomain(staticdomain);
        mv.addObject(SIDEBAR_KEY, sidebarVO);
    }

    /**
     * 相册的侧边栏
     *
     * @param mv
     */
    protected void setSidebarByPhoto(ModelAndView mv, String id) {
        SidebarVO sidebarVO = new SidebarVO();
        List<CategoryDTO> categoryDTOS = categorService.getAllCategoryByType(4L);
        PhotoDTO photoDTO = photoService.getPhotoById(id);
        if (categoryDTOS != null && categoryDTOS.size() > 0) {
            List<CategoriesVo> categories = new ArrayList<>();
            for (CategoryDTO c : categoryDTOS
            ) {
                CategoriesVo categoriesVo = new CategoriesVo();
                categoriesVo.setCategoriesName(c.getZhName());
                categoriesVo.setCnt(photoService.getCountByCategoryId(c.getId()));
                categoriesVo.setLink(domain + "/cat/Photo/" + c.getEnName());
                if (photoDTO != null && c.getId().equals(photoDTO.getCategoryId())) {
                    categoriesVo.setActiv(true);
                } else {
                    categoriesVo.setActiv(false);
                }
                categories.add(categoriesVo);
            }
            sidebarVO.setCategories(categories);
        }
        //[TODO]标签类
        sidebarVO.setStaticdomain(staticdomain);
        mv.addObject(SIDEBAR_KEY, sidebarVO);
    }
}
