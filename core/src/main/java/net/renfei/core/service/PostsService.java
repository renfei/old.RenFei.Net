package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.persistences.PostsDOMapper;
import net.renfei.dao.entity.PostsDOExample;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.core.entity.PostsDTO;
import net.renfei.core.entity.PostsListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 文章服务
 */
@Service
public class PostsService extends BaseService {
    @Autowired
    private PostsDOMapper postsDOMapper;

    /**
     * 获取文章列表
     *
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    public PostsListDTO getAllPosts(String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        PostsDOExample postsDOExample = new PostsDOExample();
        postsDOExample.setOrderByClause("release_time DESC");
        postsDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        Page page = PageHelper.startPage(intPage, intRows);
        List<PostsDOWithBLOBs> postsDOWithBLOBs = postsDOMapper.selectByExampleWithBLOBs(postsDOExample);
        for (PostsDOWithBLOBs p : postsDOWithBLOBs
        ) {
            if (stringUtil.isEmpty(p.getDescribes())) {
                p.setDescribes(stringUtil.getTextFromHtml(p.getContent()));
            }
        }
        return convert(postsDOWithBLOBs, page.getTotal());
    }

    public PostsListDTO getAllPostsByCatID(Long catID, String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        PostsDOExample postsDOExample = new PostsDOExample();
        postsDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false)
                .andCategoryIdEqualTo(catID)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        Page page = PageHelper.startPage(intPage, intRows);
        return convert(postsDOMapper.selectByExampleWithBLOBs(postsDOExample), page.getTotal());
    }

    /**
     * 根据ID获取文章
     *
     * @param id
     * @return
     */
    public PostsDTO getPostsByID(String id) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                PostsDOWithBLOBs postsDOWithBLOBs = postsDOMapper.selectByPrimaryKey(ID);
                return ejbGenerator.convert(postsDOWithBLOBs, PostsDTO.class);
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }

    private PostsListDTO convert(List<PostsDOWithBLOBs> postsDOWithBLOBs, Long count) {
        PostsListDTO postsListDTO = new PostsListDTO();
        postsListDTO.setPostsList(postsDOWithBLOBs);
        postsListDTO.setCount(count);
        return postsListDTO;
    }
}
