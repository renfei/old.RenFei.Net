package net.renfei.web.controller;

import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.service.CategorService;
import net.renfei.web.baseclass.BaseController;
import net.renfei.core.entity.PostsDTO;
import net.renfei.core.entity.PostsListDTO;
import net.renfei.web.entity.CategoryVO;
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
    @Autowired
    private PostsService postsService;
    @Autowired
    private CategorService categorService;

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
        List<PostsVO> postsVOList = ejbGenerator.convert(postsListDTO.getPostsList(), PostsVO.class);
        mv.addObject("postsVOList", postsVOList);
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
            mv.addObject("postsVO", postsVO);
            setHead(mv, postsVO.getTitle(), postsVO.getDescribes());
            if (postsVO.getContent().indexOf("<pre class=") != -1) {
                //检测到有代码显示，需要增加代码高亮插件
                setHighlightJS(mv);
            }
            setPostsPageJSCSS(mv);
        } else {
            throwNoHandlerFoundException();
        }
        mv.setViewName("posts/post");
        return mv;
    }
}
