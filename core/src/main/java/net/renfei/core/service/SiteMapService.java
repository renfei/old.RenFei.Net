package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.*;
import net.renfei.dao.entity.PhotoDOWithBLOBs;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<SiteMapXml> getSiteMaps() {
        String domain = globalService.getDomain();
        List<SiteMapXml> siteMapXmls = null;
        if (siteMapXmls == null) {
            siteMapXmls = new ArrayList<SiteMapXml>();
            //首页
            siteMapXmls.add(new SiteMapXml(domain, Changefreq.daily, "1"));
            //静态页
            siteMapXmls.add(new SiteMapXml(domain + "/posts", Changefreq.daily, "1"));
            siteMapXmls.add(new SiteMapXml(domain + "/video", Changefreq.monthly, "0.95"));
            siteMapXmls.add(new SiteMapXml(domain + "/photo", Changefreq.weekly, "0.95"));
            siteMapXmls.add(new SiteMapXml(domain + "/kitbox", Changefreq.monthly, "0.91"));
            //文章
            PostsListDTO postsListDTO = postsService.getAllPosts("1", "999999");
            if (postsListDTO.getPostsList() != null && postsListDTO.getPostsList().size() > 0) {
                for (PostsDOWithBLOBs post : postsListDTO.getPostsList()
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/posts/" + post.getId(), Changefreq.daily, "0.9", post.getReleaseTime()));
                }
            }
            //视频
            VideoListDTO videoListDTO = videoService.getAllVideo("1", "999999");
            if (videoListDTO.getVideos() != null && videoListDTO.getVideos().size() > 0) {
                for (VideoDOWithBLOBs video : videoListDTO.getVideos()
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/video/" + video.getId(), Changefreq.daily, "0.9", video.getReleaseTime()));
                }
            }
            //相册
            PhotoListDTO photoListDTO = photoService.getAllPhotos("1", "999999");
            if (photoListDTO.getPhotoDOWithBLOBs() != null && photoListDTO.getPhotoDOWithBLOBs().size() > 0) {
                for (PhotoDOWithBLOBs photo : photoListDTO.getPhotoDOWithBLOBs()
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/photo/" + photo.getId(), Changefreq.daily, "0.9", photo.getReleaseTime()));
                }
            }
        }
        return siteMapXmls;
    }
}
