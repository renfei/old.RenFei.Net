package net.renfei.web.controller;

import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.entity.CommentDTO;
import net.renfei.core.service.CategorService;
import net.renfei.core.service.CommentsService;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import net.renfei.core.entity.PostsDTO;
import net.renfei.core.entity.PostsListDTO;
import net.renfei.web.entity.CategoryVO;
import net.renfei.web.entity.CommentVO;
import net.renfei.web.entity.PostsVO;
import net.renfei.core.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

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
        setHead(mv, "Posts", "The RenFei Blog");
        setPagination(mv, page, postsListDTO.getCount(), "/posts?page=");
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
        PostsDTO postsDTO = postsService.getPostsByID(id);
        if (postsDTO != null) {
            PostsVO postsVO = ejbGenerator.convert(postsDTO, PostsVO.class);
            setInfo(postsVO);
            mv.addObject("postsVO", postsVO);
            setHead(mv, postsVO.getTitle() +" - Posts", postsVO.getDescribes());
            if (postsVO.getContent().indexOf("<pre class=") != -1) {
                //检测到有代码显示，需要增加代码高亮插件
                setHighlightJS(mv);
            }
            setPostsPageJSCSS(mv);
            //查询评论
            setComment(mv, 1L, postsDTO.getId());
            setSidebarByPost(mv, postsDTO.getId().toString());
        } else {
            throwNoHandlerFoundException();
        }
        mv.setViewName("posts/post");
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
