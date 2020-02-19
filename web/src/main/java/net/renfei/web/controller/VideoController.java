package net.renfei.web.controller;

import net.renfei.core.entity.CategoryDTO;
import net.renfei.core.entity.VideoDTO;
import net.renfei.core.entity.VideoListDTO;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.ShareVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/video")
public class VideoController extends BaseController {

    @RequestMapping("")
    public ModelAndView getAllVideoList(@RequestParam(value = "page", required = false) String page,
                                        ModelAndView mv) {
        VideoListDTO videoListDTO = videoService.getAllVideo(page, "10");
        List<VideoDTO> videoDTOList = new ArrayList<>();
        for (VideoDOWithBLOBs v : videoListDTO.getVideos()
        ) {
            VideoDTO videoDTO = ejbGenerator.convert(v, VideoDTO.class);
            setInfo(videoDTO);
            videoDTOList.add(videoDTO);
        }
        setHead(mv, "Video", "The RenFei Vlog");
        setPagination(mv, page, videoListDTO.getCount(), "/video?page=");
        mv.addObject("videoDTOList", videoDTOList);
        setSidebarByVideo(mv, null);
        mv.setViewName("video/list");
        return mv;
    }

    @RequestMapping("{id}")
    public ModelAndView playVideo(@PathVariable("id") String id, ModelAndView mv) throws NoHandlerFoundException {
        setVideoJS(mv);
        VideoDTO videoDTO = videoService.getVideoByID(id);
        if (videoDTO != null) {
            setInfo(videoDTO);
            mv.addObject("video", videoDTO);
            setHead(mv, videoDTO.getTitle()+" - Video", videoDTO.getTitle());
            setComment(mv, 3L, videoDTO.getId());
            setSidebarByVideo(mv, id);
            ShareVO shareVO = new ShareVO();
            shareVO.setTitle(videoDTO.getTitle());
            shareVO.setUrl(domain + "/video/" + videoDTO.getId());
            shareVO.setDescribes(videoDTO.getDescribes());
            shareVO.setPics(videoDTO.getFeaturedImage());
            mv.addObject("sharevo", shareVO);
            mv.setViewName("video/play");
        } else {
            throwNoHandlerFoundException();
        }
        return mv;
    }

    private void setInfo(VideoDTO videoDTO) {
        CategoryDTO categoryDTO = categorService.getCategoryByID(videoDTO.getCategoryId());
        videoDTO.setCategoryZhName(categoryDTO.getZhName());
        videoDTO.setCategoryEnName(categoryDTO.getEnName());
        videoDTO.setCategoryTypeName(categoryDTO.getTypeName());
        videoDTO.setComments(commentsService.getCommentNumber(3L, videoDTO.getId()));
    }
}
