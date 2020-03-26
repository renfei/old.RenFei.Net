package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 点赞服务
 *
 * @author RenFei
 */
@Service
public class ThumbsService extends BaseService {
    @Autowired
    private PostsService postsService;
    @Autowired
    private PageService pageService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private MoviesService moviesService;
    @Autowired
    private WeiboService weiboService;

    /**
     * 点赞服务
     *
     * @param typeid 系统分类
     * @param type   点赞还是点踩
     * @param id     对象ID
     */
    @Async
    public void thumbs(int typeid, String type, String id) {
        switch (typeid) {
            case 1:
                //Posts
                if (isUp(type)) {
                    postsService.thumbsUp(id);
                } else {
                    postsService.thumbsDown(id);
                }
                break;
            case 2:
                //Pages
                if (isUp(type)) {
                    pageService.thumbsUp(id);
                } else {
                    pageService.thumbsDown(id);
                }
                break;
            case 3:
                //Video
                if (isUp(type)) {
                    videoService.thumbsUp(id);
                } else {
                    videoService.thumbsDown(id);
                }
                break;
            case 4:
                //Photo
                break;
            case 5:
                //Movie
                if (isUp(type)) {
                    moviesService.thumbsUp(id);
                } else {
                    moviesService.thumbsDown(id);
                }
                break;
            case 6:
                //WeiBo
                if (isUp(type)) {
                    weiboService.thumbsUp(id);
                } else {
                    weiboService.thumbsDown(id);
                }
                break;
            default:
                break;
        }
    }

    private boolean isUp(String type) {
        if (stringUtil.isEmpty(type)) {
            return true;
        } else if ("down".equals(type.trim().toLowerCase())) {
            return false;
        }
        return true;
    }
}
