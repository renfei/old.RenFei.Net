package net.renfei.www.controller;

import net.renfei.www.common.baseclass.BaseController;
import net.renfei.www.entity.dto.PostsDTO;
import net.renfei.www.entity.dto.PostsListDTO;
import net.renfei.www.entity.vo.PostsVO;
import net.renfei.www.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 文章的Controller
 */
@Controller
@RequestMapping("/posts")
public class PostsController extends BaseController {
    @Autowired
    private PostsService postsService;

    /**
     * 获取所有文章列表
     *
     * @param page 页码
     * @param rows 每页容量
     * @return
     */
    @RequestMapping("")
    public ModelAndView getAllPostsList(@RequestParam(value = "page", required = false) String page,
                                        @RequestParam(value = "rows", required = false) String rows) {
        ModelAndView mv = new ModelAndView();
        PostsListDTO postsListDTO = postsService.getAllPosts(page, rows);
        List<PostsVO> postsVOList = ejbGenerator.convert(postsListDTO.getPostsList(), PostsVO.class);
        //[TODO]还需要每个文章的分类和去掉HTML标签
        mv.addObject("postsVOList", postsVOList);
        return mv;
    }

    /**
     * 获取所有分类列表
     *
     * @param page 页码
     * @param rows 每页容量
     * @return
     */
    @RequestMapping("cat")
    public ModelAndView getAllCatList(@RequestParam(value = "page", required = false) String page,
                                      @RequestParam(value = "rows", required = false) String rows) {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    /**
     * 根据分类ID获取分类下面所有文章列表
     *
     * @param catID 分类ID
     * @param page  页码
     * @param rows  每页容量
     * @return
     */
    @RequestMapping("cat/{id}")
    public ModelAndView getPostsListByCatID(@PathVariable("id") String catID,
                                            @RequestParam(value = "page", required = false) int page,
                                            @RequestParam(value = "rows", required = false) int rows) {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    /**
     * 获取所有标签列表
     *
     * @param page 页码
     * @param rows 每页容量
     * @return
     */
    @RequestMapping("tag")
    public ModelAndView getAllTagList(@RequestParam(value = "page", required = false) int page,
                                      @RequestParam(value = "rows", required = false) int rows) {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

    /**
     * 根据标签ID获取标签下面所有文章列表
     *
     * @param tagID 标签ID
     * @param page  页码
     * @param rows  每页容量
     * @return
     */
    @RequestMapping("tag/{id}")
    public ModelAndView getPostsListByTagID(@PathVariable("id") String tagID,
                                            @RequestParam(value = "page", required = false) int page,
                                            @RequestParam(value = "rows", required = false) int rows) {
        ModelAndView mv = new ModelAndView();
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
