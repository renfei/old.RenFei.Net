package net.renfei.web.controller;

import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.entity.CommentDTO;
import net.renfei.core.service.CategorService;
import net.renfei.core.service.CommentsService;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.entity.TagDO;
import net.renfei.dao.entity.TagRelationDO;
import net.renfei.web.baseclass.BaseController;
import net.renfei.core.entity.PostsDTO;
import net.renfei.core.entity.PostsListDTO;
import net.renfei.web.entity.*;
import net.renfei.core.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文章的Controller
 */
@Controller
@RequestMapping("/posts")
public class PostsController extends BaseController {


    /**
     * 获取所有文章列表
     *
     * @param page 页码
     * @return
     */
    @RequestMapping("")
    public ModelAndView getAllPostsList(@RequestParam(value = "page", required = false) String page,
                                        ModelAndView mv) {
        PostsListDTO postsListDTO = postsService.getAllPosts(page, "10");
        List<PostsVO> postsVOList = new ArrayList<>();
        for (PostsDOWithBLOBs post : postsListDTO.getPostsList()
        ) {
            PostsVO postsVO = ejbGenerator.convert(post, PostsVO.class);
            setInfo(postsVO);
            postsVOList.add(postsVO);
        }
        mv.addObject("postsVOList", postsVOList);
        setHead(mv, "Posts",
                "任霏的个人博客，分享关注软件开发技术，推动并帮助开发者通过互联网获得知识，从而让更多开发者从中受益。",
                "任霏,RenFei,NeilRen,个人,博客,blog,开发,技术,posts");
        setPagination(mv, page, postsListDTO.getCount(), "/posts?page=");
        setSidebarByPost(mv, null);
        mv.setViewName("posts/list");
        return mv;
    }

    /**
     * 根据标签获取所有文章列表
     *
     * @param page 页码
     * @return
     */
    @RequestMapping("tag/{enName}")
    public ModelAndView getAllPostsListByTag(@RequestParam(value = "page", required = false) String page,
                                             @PathVariable("enName") String enName,
                                             ModelAndView mv) throws NoHandlerFoundException {
        List<TagDO> tagDOS = tagService.getTagByEnName(enName);
        if (tagDOS == null || tagDOS.size() < 1) {
            throwNoHandlerFoundException();
        }
        List<TagRelationDO> tagRelationDOS = tagService.getTagRelationByEnName(enName, 1L);
        if (tagRelationDOS == null || tagRelationDOS.size() < 1) {
            throwNoHandlerFoundException();
        }
        PostsListDTO postsListDTO = postsService.getAllPostsByTag(page, "10", enName);
        if (postsListDTO == null || postsListDTO.getCount() < 1) {
            throwNoHandlerFoundException();
        }
        List<PostsVO> postsVOList = new ArrayList<>();
        for (PostsDOWithBLOBs post : postsListDTO.getPostsList()
        ) {
            PostsVO postsVO = ejbGenerator.convert(post, PostsVO.class);
            setInfo(postsVO);
            postsVOList.add(postsVO);
        }
        mv.addObject("postsVOList", postsVOList);
        setHead(mv, "Tag:" + tagDOS.get(0).getZhName() + " - Posts",
                "博客文章标签分类：" + tagDOS.get(0).getZhName() + "。共同类型的文章在这里聚合等待您的查阅。",
                tagDOS.get(0).getZhName() + ",博客,blog,开发,技术,posts");
        setPagination(mv, page, postsListDTO.getCount(), "/posts/tag/" + enName + "?page=");
        setSidebarByPost(mv, null);
        mv.setViewName("posts/list");
        return mv;
    }

    /**
     * 根据文章ID获取文章详情
     *
     * @param id
     * @return
     */
    @RequestMapping("{id}")
    public ModelAndView getPostsByID(@PathVariable("id") String id, ModelAndView mv) throws NoHandlerFoundException {
        PostsDTO postsDTO = postsService.getPostsByID(id, true);
        if (postsDTO != null) {
            postsService.addAds(postsDTO);
            PostsVO postsVO = ejbGenerator.convert(postsDTO, PostsVO.class);
            setInfo(postsVO);
            mv.addObject("postsVO", postsVO);
            OGprotocol opg = new OGprotocol();
            opg.setType("article");
            opg.setAuthor(postsVO.getSourceName());
            opg.setDescription(postsVO.getDescribes());
            opg.setImage(postsVO.getFeaturedImage());
            opg.setLocale("zh_CN");
            opg.setReleaseDate(postsVO.getReleaseTime());
            opg.setSiteName("RenFei.Net");
            opg.setTitle(postsVO.getTitle() + " - Posts");
            opg.setUrl(domain + "/posts/" + postsVO.getId());
            setHead(mv, postsVO.getTitle() + " - Posts", postsVO.getDescribes(),
                    postsVO.getKeyword(), opg);
            if (postsVO.getContent().indexOf("code class=") != -1) {
                //检测到有代码显示，需要增加代码高亮插件
                setHighlightJS(mv);
            }
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
            setComment(mv, 1L, postsDTO.getId());
            setSidebarByPost(mv, postsDTO.getId().toString());
            //获得相关文章
            mv.addObject("related", postsService.getRelated(id));
            //获取文章扩展服务
            postsService.getPostsExtraByID(id, mv);
            ShareVO shareVO = new ShareVO();
            shareVO.setTitle(postsVO.getTitle());
            shareVO.setUrl(domain + "/posts/" + postsVO.getId());
            shareVO.setDescribes(postsVO.getDescribes());
            shareVO.setPics(postsVO.getFeaturedImage());
            mv.addObject("sharevo", shareVO);
        } else {
            throwNoHandlerFoundException();
        }
        mv.setViewName("posts/post");
        mv.addObject("jsonld", postsService.getJsonld(postsDTO));
        return mv;
    }

    private void setInfo(PostsVO postsVO) {
        CategoryDTO categoryDTO = categorService.getCategoryByID(postsVO.getCategoryId());
        postsVO.setCategoryZhName(categoryDTO.getZhName());
        postsVO.setCategoryEnName(categoryDTO.getEnName());
        postsVO.setCategoryTypeName(categoryDTO.getTypeName());
        postsVO.setComments(commentsService.getCommentNumber(1L, postsVO.getId()));
    }
}
