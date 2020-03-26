package net.renfei.web.controller;

import net.renfei.core.entity.WeiboDTO;
import net.renfei.core.service.JsonLdService;
import net.renfei.dao.entity.WeiboDO;
import net.renfei.dao.entity.WeiboDOS;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.OGprotocol;
import net.renfei.web.entity.PageHeadVO;
import net.renfei.web.entity.ShareVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/weibo")
public class WeiboController extends BaseController {
    @Autowired
    private JsonLdService jsonLdService;
    @RequestMapping("")
    public ModelAndView weibo(@RequestParam(value = "page", required = false) String page,
                              ModelAndView mv) {
        WeiboDTO weiboDTO = weiboService.getAllPosts(page, "10");
        Map<String, Object> map = mv.getModel();
        PageHeadVO pageHeadVO = null;
        Object obj = map.get(HEAD_KEY);
        if (obj instanceof PageHeadVO) {
            pageHeadVO = (PageHeadVO) obj;
        }
        if (pageHeadVO != null) {
            List<String> jss = pageHeadVO.getJss();
            jss.add("//" + staticdomain + "/js/baguetteBox.min.js");
            pageHeadVO.setJss(jss);
            List<String> css = pageHeadVO.getCss();
            css.add("//" + staticdomain + "/css/baguetteBox.min.css");
            pageHeadVO.setCss(css);
            mv.addObject(HEAD_KEY, pageHeadVO);
        }
        setPostsPageJSCSS(mv);
        mv.addObject("weiboDTO", weiboDTO);
        setHead(mv, "任霏的微博客 - Weibo",
                "任霏的的微型博客和微型网志。",
                "任霏,RenFei,NeilRen,个人,博客,blog,开发,技术,posts");
        setPagination(mv, page, weiboDTO.getCount(), "/weibo?page=");
        mv.setViewName("weibo/index");
        return mv;
    }

    /**
     * 根据微博ID获取微博详情
     *
     * @param id
     * @return
     */
    @RequestMapping("{id}")
    public ModelAndView getPostsByID(@PathVariable("id") String id, ModelAndView mv) throws NoHandlerFoundException {
        WeiboDO weiboDO = weiboService.getWeiboById(id, true);
        if (weiboDO != null) {
            WeiboDOS weiboDOS = ejbGenerator.convert(weiboDO, WeiboDOS.class);
            weiboService.setImg(weiboDOS);
            setInfo(weiboDOS);
            mv.addObject("weibo", weiboDOS);
            OGprotocol opg = new OGprotocol();
            opg.setType("article");
            opg.setAuthor("任霏");
            opg.setDescription(weiboDOS.getContent());
            if (weiboDOS.getImg() != null) {
                opg.setImage(weiboDOS.getImg());
            }
            opg.setLocale("zh_CN");
            opg.setReleaseDate(weiboDOS.getReleaseTime());
            opg.setSiteName("RenFei.Net");
            opg.setTitle(getTitle(weiboDOS.getContent()) + " - Weibo");
            opg.setUrl(domain + "/weibo/" + weiboDOS.getId());
            setHead(mv, getTitle(weiboDOS.getContent()) + " - Weibo", weiboDOS.getContent(),
                    "", opg);
            Map<String, Object> map = mv.getModel();
            PageHeadVO pageHeadVO = null;
            Object obj = map.get(HEAD_KEY);
            if (obj instanceof PageHeadVO) {
                pageHeadVO = (PageHeadVO) obj;
            }
            if (pageHeadVO != null) {
                List<String> jss = pageHeadVO.getJss();
                jss.add("//" + staticdomain + "/js/baguetteBox.min.js");
                pageHeadVO.setJss(jss);
                List<String> css = pageHeadVO.getCss();
                css.add("//" + staticdomain + "/css/baguetteBox.min.css");
                css.add("//" + staticdomain + "/css/gallery-clean.css");
                pageHeadVO.setCss(css);
                mv.addObject(HEAD_KEY, pageHeadVO);
            }
            setPostsPageJSCSS(mv);
            //查询评论
            setComment(mv, 6L, weiboDO.getId());
            ShareVO shareVO = new ShareVO();
            shareVO.setTitle(getTitle(weiboDOS.getContent()));
            shareVO.setUrl(domain + "/weibo/" + weiboDOS.getId());
            shareVO.setDescribes(weiboDOS.getContent());
            shareVO.setPics(weiboDOS.getImg());
            mv.addObject("sharevo", shareVO);
            mv.addObject("jsonld", jsonLdService.getJsonld(weiboDOS));
        } else {
            throwNoHandlerFoundException();
        }
        mv.setViewName("weibo/weibo");
        return mv;
    }

    private void setInfo(WeiboDOS weiboDOS) {
        weiboDOS.setComments(commentsService.getCommentNumber(6L, weiboDOS.getId()));
    }

    private String getTitle(String content) {
        if (content.length() < 100) {
            return content;
        } else {
            return content.substring(0, 100);
        }
    }
}
