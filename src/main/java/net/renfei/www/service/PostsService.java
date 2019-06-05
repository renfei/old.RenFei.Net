package net.renfei.www.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.www.common.baseclass.BaseService;
import net.renfei.www.dao.PostsDOMapper;
import net.renfei.www.entity.dto.PostsListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        int intPage = DEFAULT_PAGE, intRows = DEFAULT_ROWS;
        if (!stringUtil.isEmpty(pages)) {
            try {
                intPage = Integer.valueOf(pages);
            } catch (NumberFormatException nfe) {
                intPage = DEFAULT_PAGE;
            }
        }
        if (!stringUtil.isEmpty(rows)) {
            try {
                intRows = Integer.valueOf(rows);
            } catch (NumberFormatException nfe) {
                intRows = DEFAULT_ROWS;
            }
        }
        PostsListDTO postsListDTO = new PostsListDTO();
        Page page = PageHelper.startPage(intPage, intRows, true);
        postsListDTO.setPostsList(postsDOMapper.selectAllPosts());
        postsListDTO.setCount(page.getTotal());
        return postsListDTO;
    }
}
