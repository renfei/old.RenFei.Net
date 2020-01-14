package net.renfei.web.controller.api;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.renfei.core.entity.PostsDTO;
import net.renfei.core.service.PostsService;
import net.renfei.core.service.TagService;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.web.baseclass.BaseRestController;
import net.renfei.web.entity.APIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostsAPIController extends BaseRestController {
    @Autowired
    private PostsService postsService;
    @Autowired
    private TagService tagService;

    @GetMapping("all")
    public APIResult getAllPost(String pages, String rows) {
        try {
            return APIResult.fillResult(true, "", postsService.getAdminPosts(pages, rows, "release_time DESC"));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }

    @GetMapping("")
    public APIResult getPost(Long id) {
        try {
            return APIResult.fillResult(true, "", postsService.getPostsByID(id.toString(), false));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }

    @PostMapping("")
    public APIResult addPost(String posts, String tags) {
        try {
            PostsDOWithBLOBs post = JSON.parseObject(posts, PostsDOWithBLOBs.class);
            String[] sTags = tags.split(",");
            post = postsService.addPost(post);
            for (String i : sTags
            ) {
                tagService.addTagRelation(Integer.valueOf(i), 1L, post.getId());
            }
            return APIResult.fillResult(true, "", post);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }

    @PutMapping("")
    public APIResult updatePost(String posts, String tags) {
        try {
            PostsDOWithBLOBs post = JSON.parseObject(posts, PostsDOWithBLOBs.class);
            String[] sTags = tags.split(",");
            postsService.updatePost(post);
            tagService.deleteTagRelationByTargetId(post.getId());
            for (String i : sTags
            ) {
                tagService.addTagRelation(Integer.valueOf(i), 1L, post.getId());
            }
            return APIResult.fillResult(true, "", post.getId());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }

    @DeleteMapping("")
    public APIResult deletePost(Long id) {
        try {
            PostsDTO postsDTO = postsService.getPostsByID(id.toString(),false);
            postsDTO.setIsDelete(true);
            postsService.updatePost(postsDTO);
            return APIResult.fillResult(true);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return APIResult.fillResult(false, ex.getMessage());
        }
    }
}
