package net.renfei.web.controller;

import net.renfei.core.entity.*;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.PostsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cat")
public class CatController extends BaseController {

    @RequestMapping("{typeName}/{enName}")
    public ModelAndView getList(
            @PathVariable(value = "typeName") String typeName,
            @PathVariable(value = "enName") String enName,
            @Param(value = "page") String page,
            ModelAndView mv) throws NoHandlerFoundException {
        CategoryDTO categoryDTO = categorService.getCategoryByEnNaeme(enName);
        if (categoryDTO != null) {
            switch (typeName.toLowerCase()) {
                case "posts":
                    PostsListDTO postsListDTO = postsService.getAllPostsByCatID(categoryDTO.getId(), page, "10");
                    List<PostsVO> postsVOList = new ArrayList<>();
                    for (PostsDOWithBLOBs post : postsListDTO.getPostsList()
                    ) {
                        PostsVO postsVO = ejbGenerator.convert(post, PostsVO.class);
                        setInfo(postsVO);
                        postsVOList.add(postsVO);
                    }
                    mv.addObject("postsVOList", postsVOList);
                    setHead(mv, "Posts", "The RenFei Blog");
                    setPagination(mv, page, postsListDTO.getCount(), "/cat/" + typeName + "/" + enName + "?page=");
                    setSidebarByPost(mv, null);
                    mv.setViewName("posts/list");
                    break;
                case "video":
                    VideoListDTO videoListDTO = videoService.getAllVideoByCatID(categoryDTO.getId(), page, "10");
                    List<VideoDTO> videoDTOList = new ArrayList<>();
                    for (VideoDOWithBLOBs v : videoListDTO.getVideos()
                    ) {
                        VideoDTO videoDTO = ejbGenerator.convert(v, VideoDTO.class);
                        setInfo(videoDTO);
                        videoDTOList.add(videoDTO);
                    }
                    setHead(mv, "Video", "The RenFei Vlog");
                    setPagination(mv, page, videoListDTO.getCount(), "/cat/" + typeName + "/" + enName + "?page=");
                    mv.addObject("videoDTOList", videoDTOList);
                    setSidebarByVideo(mv, null);
                    mv.setViewName("video/list");
                    break;
                case "photo":
                    setHead(mv, "Photo", "The RenFei Web Albums");
                    PhotoListDTO photoListDTO = photoService.getAllPhotosCatID(categoryDTO.getId(), page, "10");
                    mv.addObject("photoList", photoListDTO.getPhotoDOWithBLOBs());
                    setPagination(mv, page, photoListDTO.getCount(), "/cat/" + typeName + "/" + enName + "?page=");
                    setSidebarByPhoto(mv, null);
                    mv.setViewName("photo/list");
                    break;
                default:
                    throwNoHandlerFoundException();
                    break;
            }
        } else {
            throwNoHandlerFoundException();
        }
        return mv;
    }

    private void setInfo(PostsVO postsVO) {
        CategoryDTO categoryDTO = categorService.getCategoryByID(postsVO.getCategoryId());
        postsVO.setCategoryZhName(categoryDTO.getZhName());
        postsVO.setCategoryEnName(categoryDTO.getEnName());
        postsVO.setCategoryTypeName(categoryDTO.getTypeName());
        postsVO.setComments(commentsService.getCommentNumber(1L, postsVO.getId()));
    }

    private void setInfo(VideoDTO videoDTO) {
        CategoryDTO categoryDTO = categorService.getCategoryByID(videoDTO.getCategoryId());
        videoDTO.setCategoryZhName(categoryDTO.getZhName());
        videoDTO.setCategoryEnName(categoryDTO.getEnName());
        videoDTO.setCategoryTypeName(categoryDTO.getTypeName());
        videoDTO.setComments(commentsService.getCommentNumber(3L, videoDTO.getId()));
    }
}
