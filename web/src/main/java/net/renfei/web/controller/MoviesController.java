package net.renfei.web.controller;

import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.entity.MovieClassificationDTO;
import net.renfei.core.entity.MoviesListDTO;
import net.renfei.dao.entity.MovieDOWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

/**
 * 影视的Controller
 */
@Controller
@RequestMapping("/movie")
public class MoviesController extends BaseController {
    /**
     * 获取所有影视列表
     *
     * @param page 页码
     * @return
     */
    @RequestMapping("")
    public ModelAndView getAllMoviesList(@RequestParam(value = "page", required = false) String page,
                                         ModelAndView mv) {
        MoviesListDTO moviesListDTO = moviesService.getAllMovies(page, "12");
        List<MovieClassificationDTO> movieClassificationDTOS = moviesService.getAllMovieClassification();
        mv.addObject("moviesListDTO", moviesListDTO);
        mv.addObject("movieClassificationDTOS", movieClassificationDTOS);
        mv.addObject("movieClassName", "");
        setPagination(mv, page, moviesListDTO.getCount(), "/movie?page=");
        setHead(mv, "休闲影视频道", "收集分享电影影视作品，一起看电影！");
        mv.setViewName("movies/list");
        return mv;
    }

    /**
     * 获取所有影视列表
     *
     * @param page 页码
     * @return
     */
    @RequestMapping("cat/{catname}")
    public ModelAndView getMoviesListByCat(@PathVariable("catname") String catname,
                                           @RequestParam(value = "page", required = false) String page,
                                           ModelAndView mv) throws NoHandlerFoundException {
        MoviesListDTO moviesListDTO = moviesService.getMoviesListByCat(catname, page, "12");
        if (moviesListDTO != null) {
            CategoryDTO categoryDTO = categorService.getCategoryByEnNaeme(catname);
            List<MovieClassificationDTO> movieClassificationDTOS = moviesService.getAllMovieClassification();
            mv.addObject("moviesListDTO", moviesListDTO);
            mv.addObject("movieClassificationDTOS", movieClassificationDTOS);
            mv.addObject("movieClassName", categoryDTO.getEnName());
            setPagination(mv, page, moviesListDTO.getCount(), "/movie?page=");
            setHead(mv, categoryDTO.getZhName() + " - 休闲影视频道", "收集分享电影影视作品，一起看电影！");
            mv.setViewName("movies/list");
            return mv;
        } else {
            throwNoHandlerFoundException();
            return mv;
        }
    }

    /**
     * 根据电影ID获取电影详情
     *
     * @param id
     * @return
     */
    @RequestMapping("view/{id}")
    public ModelAndView getPostsByID(@PathVariable("id") String id, ModelAndView mv) throws NoHandlerFoundException {
        MovieDOWithBLOBs movieDOWithBLOBs = moviesService.getMovieById(id, true);
        if (movieDOWithBLOBs != null) {
            setHead(mv, movieDOWithBLOBs.getName() + " - 休闲影视频道", movieDOWithBLOBs.getSynopsis());
            mv.addObject("movieDOWithBLOBs", movieDOWithBLOBs);
        } else {
            throwNoHandlerFoundException();
        }
        mv.setViewName("movies/view");
        return mv;
    }
}
