package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.*;
import net.renfei.dao.entity.PhotoDOWithBLOBs;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SiteMapService extends BaseService {
    @Autowired
    private GlobalService globalService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private IndexService indexService;

    public List<SiteMapXml> getSiteMaps() {
        String domain = globalService.getDomain();
        List<SiteMapXml> siteMapXmls = null;
        if (siteMapXmls == null) {
            siteMapXmls = new ArrayList<SiteMapXml>();
            //首页
            AllInfoDTOList allInfoDTOList = indexService.getAllInfo("1", "1");
            if (allInfoDTOList.getVAllInfoWithBLOBsList() != null && allInfoDTOList.getVAllInfoWithBLOBsList().size() > 0) {
                siteMapXmls.add(new SiteMapXml(domain, Changefreq.daily, "1", allInfoDTOList.getVAllInfoWithBLOBsList().get(0).getReleaseTime()));
            } else {
                siteMapXmls.add(new SiteMapXml(domain, Changefreq.daily, "1", new Date()));
            }
            //文章
            PostsListDTO postsListDTO = postsService.getAllPosts("1", "999999");
            if (postsListDTO.getPostsList() != null && postsListDTO.getPostsList().size() > 0) {
                siteMapXmls.add(new SiteMapXml(domain + "/posts", Changefreq.daily, "1", postsListDTO.getPostsList().get(0).getReleaseTime()));
                for (PostsDOWithBLOBs post : postsListDTO.getPostsList()
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/posts/" + post.getId(), Changefreq.daily, "0.9", post.getReleaseTime()));
                }
            }
            //视频
            VideoListDTO videoListDTO = videoService.getAllVideo("1", "999999");
            if (videoListDTO.getVideos() != null && videoListDTO.getVideos().size() > 0) {
                siteMapXmls.add(new SiteMapXml(domain + "/video", Changefreq.monthly, "0.95", videoListDTO.getVideos().get(0).getReleaseTime()));
                for (VideoDOWithBLOBs video : videoListDTO.getVideos()
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/video/" + video.getId(), Changefreq.daily, "0.9", video.getReleaseTime()));
                }
            }
            //相册
            PhotoListDTO photoListDTO = photoService.getAllPhotos("1", "999999");
            if (photoListDTO.getPhotoDOWithBLOBs() != null && photoListDTO.getPhotoDOWithBLOBs().size() > 0) {
                siteMapXmls.add(new SiteMapXml(domain + "/photo", Changefreq.weekly, "0.95", photoListDTO.getPhotoDOWithBLOBs().get(0).getReleaseTime()));
                for (PhotoDOWithBLOBs photo : photoListDTO.getPhotoDOWithBLOBs()
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/photo/" + photo.getId(), Changefreq.daily, "0.9", photo.getReleaseTime()));
                }
            }
        }
        return siteMapXmls;
    }
}
