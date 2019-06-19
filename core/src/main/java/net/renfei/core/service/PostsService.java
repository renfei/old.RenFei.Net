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
        postsDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        PostsListDTO postsListDTO = new PostsListDTO();
        Page page = PageHelper.startPage(intPage, intRows);
        postsListDTO.setPostsList(postsDOMapper.selectByExampleWithBLOBs(postsDOExample));
        postsListDTO.setCount(page.getTotal());
        return postsListDTO;
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
}
