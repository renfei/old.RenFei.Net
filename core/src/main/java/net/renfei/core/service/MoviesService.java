package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.MovieClassificationDTO;
import net.renfei.core.entity.MoviesListDTO;
import net.renfei.dao.entity.*;
import net.renfei.util.StringUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "MoviesService")
public class MoviesService extends BaseService {

    /**
     * 获取全部电影列表
     *
     * @param pages
     * @param rows
     * @return
     */
    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1", condition = "#p0!=null&&#p1!=null")
    public MoviesListDTO getAllMovies(String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        MovieDOExample movieDOExample = new MovieDOExample();
        movieDOExample.setOrderByClause("years DESC,update_time DESC");
        movieDOExample.createCriteria();
        Page page = PageHelper.startPage(intPage, intRows);
        return convert(movieDOMapper.selectByExampleWithBLOBs(movieDOExample), page.getTotal());
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1+'_'+#p2", condition = "#p0!=null&&#p1!=null&&#p2!=null")
    public MoviesListDTO getMoviesListByCat(String catname, String pages, String rows) {
        if (stringUtil.isEmpty(catname)) {
            return null;
        }
        CategoryDOExample categoryDOExample = new CategoryDOExample();
        categoryDOExample.createCriteria().andEnNameEqualTo(catname.trim());
        List<CategoryDO> categoryDOS = categoryDOMapper.selectByExampleWithBLOBs(categoryDOExample);
        if (categoryDOS != null && categoryDOS.size() > 0) {
            int intPage = convertPage(pages), intRows = convertRows(rows);
            MovieDOExample movieDOExample = new MovieDOExample();
            movieDOExample.setOrderByClause("years DESC,update_time DESC");
            movieDOExample.createCriteria()
                    .andCategoryIdEqualTo(categoryDOS.get(0).getId());
            Page page = PageHelper.startPage(intPage, intRows);
            return convert(movieDOMapper.selectByExampleWithBLOBs(movieDOExample), page.getTotal());
        } else {
            return null;
        }

    }

    /**
     * 获得相同主演的其他电影
     *
     * @param movieDOWithBLOBs
     * @return
     */
    public List<MovieDOWithBLOBs> getSameMainActor(MovieDOWithBLOBs movieDOWithBLOBs) {
        if (movieDOWithBLOBs != null) {
            if (!stringUtil.isEmpty(movieDOWithBLOBs.getLead())) {
                String[] leads = movieDOWithBLOBs.getLead().split(",");
                if (leads.length > 0) {
                    MovieDOExample movieDOExample = new MovieDOExample();
                    movieDOExample.setOrderByClause("years DESC,update_time DESC");
                    movieDOExample.createCriteria();
                    for (String lead : leads
                    ) {
                        movieDOExample.or().andLeadLike("%" + lead + "%");
                    }
                    return movieDOMapper.selectByExampleWithBLOBs(movieDOExample);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void thumbsUp(String id) {
        MovieDOWithBLOBs movieDOWithBLOBs = getMovieById(id, false);
        if (movieDOWithBLOBs != null) {
            movieDOWithBLOBs.setThumbsUp(movieDOWithBLOBs.getThumbsUp() + 1);
            update(movieDOWithBLOBs);
        }
    }

    /**
     * 点踩
     *
     * @param id
     */
    public void thumbsDown(String id) {
        MovieDOWithBLOBs movieDOWithBLOBs = getMovieById(id, false);
        if (movieDOWithBLOBs != null) {
            movieDOWithBLOBs.setThumbsDown(movieDOWithBLOBs.getThumbsDown() + 1);
            update(movieDOWithBLOBs);
        }
    }

    public int update(MovieDOWithBLOBs movieDOWithBLOBs) {
        return movieDOMapper.updateByPrimaryKeySelective(movieDOWithBLOBs);
    }

    /**
     * 根据ID获取电影详情
     *
     * @param id
     * @param recordViews
     * @return
     */
    public MovieDOWithBLOBs getMovieById(String id, boolean recordViews) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                MovieDOWithBLOBs movieDOWithBLOBs = movieDOMapper.selectByPrimaryKey(ID);
                if (movieDOWithBLOBs != null && recordViews) {
                    updateView(movieDOWithBLOBs);
                }
                return movieDOWithBLOBs;
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }
    @Cacheable(key = "targetClass+'_'+methodName")
    public List<MovieClassificationDTO> getAllMovieClassification() {
        TypeDOExample typeDOExample = new TypeDOExample();
        typeDOExample.createCriteria().andTypeNameEqualTo("Movie");
        List<TypeDO> typeDOS = typeDOMapper.selectByExample(typeDOExample);
        if (typeDOS != null && typeDOS.size() > 0) {
            CategoryDOExample categoryDOExample = new CategoryDOExample();
            categoryDOExample.createCriteria().andTypeIdEqualTo(typeDOS.get(0).getId());
            List<CategoryDO> categoryDOS = categoryDOMapper.selectByExampleWithBLOBs(categoryDOExample);
            if (categoryDOS != null && categoryDOS.size() > 0) {
                List<MovieClassificationDTO> movieClassificationDTOS = new ArrayList<>();
                for (CategoryDO cat : categoryDOS
                ) {
                    MovieClassificationDTO movieClassificationDTO = ejbGenerator.convert(cat, MovieClassificationDTO.class);
                    MovieDOExample movieDOExample = new MovieDOExample();
                    movieDOExample.createCriteria().andCategoryIdEqualTo(cat.getId());
                    Page page = PageHelper.startPage(1, 1);
                    movieDOMapper.selectByExample(movieDOExample);
                    movieClassificationDTO.setCountMovie(page.getTotal());
                    movieClassificationDTOS.add(movieClassificationDTO);
                }
                return movieClassificationDTOS;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    private MoviesListDTO convert(List<MovieDOWithBLOBs> movieDOWithBLOBs, Long count) {
        MoviesListDTO moviesListDTO = new MoviesListDTO();
        moviesListDTO.setCount(count);
        moviesListDTO.setMovieDOList(movieDOWithBLOBs);
        return moviesListDTO;
    }
}
