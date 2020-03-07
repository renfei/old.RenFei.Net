package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.*;
import net.renfei.dao.entity.PhotoDOWithBLOBs;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import net.renfei.dao.entity.WeiboDOS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "SiteMapService")
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
    @Autowired
    private WeiboService weiboService;
    @Autowired
    private CategorService categorService;

    @Cacheable(key = "targetClass+'_'+methodName")
    public List<SiteMapXml> getSiteMaps() {
        String domain = globalService.getDomain();
        Date latestDate = new Date();
        List<SiteMapXml> siteMapXmls = null;
        if (siteMapXmls == null) {
            siteMapXmls = new ArrayList<SiteMapXml>();
            //首页
            AllInfoDTOList allInfoDTOList = indexService.getAllInfo("1", "1");
            if (allInfoDTOList.getVAllInfoWithBLOBsList() != null && allInfoDTOList.getVAllInfoWithBLOBsList().size() > 0) {
                latestDate = allInfoDTOList.getVAllInfoWithBLOBsList().get(0).getReleaseTime();
                siteMapXmls.add(new SiteMapXml(domain, Changefreq.daily, "1", allInfoDTOList.getVAllInfoWithBLOBsList().get(0).getReleaseTime()));
            } else {
                siteMapXmls.add(new SiteMapXml(domain, Changefreq.daily, "1", new Date()));
            }
            //文章
            PostsListDTO postsListDTO = postsService.getAllPosts("1", "999999", "release_time DESC");
            if (postsListDTO.getPostsList() != null && postsListDTO.getPostsList().size() > 0) {
                siteMapXmls.add(new SiteMapXml(domain + "/posts", Changefreq.daily, "1", postsListDTO.getPostsList().get(0).getReleaseTime()));
                for (PostsDOWithBLOBs post : postsListDTO.getPostsList()
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/posts/" + post.getId(), Changefreq.daily, "0.9", post.getReleaseTime()));
                }
            }
            //微博
            WeiboDTO weiboDTO = weiboService.getAllPosts("1", "999999");
            if (weiboDTO != null && weiboDTO.getWeiboDOList() != null && weiboDTO.getWeiboDOList().size() > 0) {
                siteMapXmls.add(new SiteMapXml(domain + "/weibo", Changefreq.daily, "1", weiboDTO.getWeiboDOList().get(0).getReleaseTime()));
                for (WeiboDOS weiboDOS : weiboDTO.getWeiboDOList()
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/weibo/" + weiboDOS.getId(), Changefreq.daily, "0.9", weiboDOS.getReleaseTime()));
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
            //分类
            List<CategoryDTO> categoryDTOS = categorService.getAllCategory();
            if (categoryDTOS != null && categoryDTOS.size() > 0) {
                for (CategoryDTO cat : categoryDTOS
                ) {
                    siteMapXmls.add(new SiteMapXml(domain + "/cat/" + cat.getUriPath() + "/" + cat.getEnName(), Changefreq.daily, "0.8", latestDate));
                }
            }
        }
        return siteMapXmls;
    }
}
