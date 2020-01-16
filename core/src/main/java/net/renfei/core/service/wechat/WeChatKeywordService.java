package net.renfei.core.service.wechat;

import com.alibaba.fastjson.JSON;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.PhotoDTO;
import net.renfei.core.entity.PostsDTO;
import net.renfei.core.entity.SearchDTO;
import net.renfei.core.entity.VideoDTO;
import net.renfei.core.entity.wechat.*;
import net.renfei.core.service.*;
import net.renfei.dao.entity.DownloadDO;
import net.renfei.dao.entity.FullTextIndexDO;
import net.renfei.dao.entity.WeChatKeywordDO;
import net.renfei.dao.entity.WeChatKeywordDOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WeChatKeywordService extends BaseService {
    @Autowired
    private WeChatMessageService weChatMessageService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private GlobalService globalService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private DownloadService downloadService;

    public String talk(WeChatMessage weChatMessage) {
        // 默认回复文本消息
        TextMessage textMessage = new TextMessage(weChatMessage);
        if (weChatMessage.getMessageType() == WeChatMessageType.TEXT ||
                weChatMessage.getMessageType() == WeChatMessageType.VOICE) {
            WeChatKeywordDO keyword = getWeChatKeyword(weChatMessage.getMessage());
            if (keyword != null) {
                if ("text".equals(keyword.getKeyTypw().toLowerCase())) {
                    textMessage.setContent(keyword.getKeyValue());
                    return weChatMessageService.textMessageToXml(textMessage);
                } else {
                    //执行其他预留命令
                    return Search(weChatMessage);
                }
            } else {
                String result = JiSuDownload(weChatMessage);//极速下载服务
                if (result != null) {
                    return result;
                }
                return Search(weChatMessage);
            }
        } else {
            textMessage.setContent("我们已经收到您发送的消息，但「RENFEI.NET」暂时还无法理解其中的含义，请您尝试发送文本消息。");
            return weChatMessageService.textMessageToXml(textMessage);
        }
    }

    private String Search(WeChatMessage weChatMessage) {
        // 默认回复文本消息
        TextMessage textMessage = new TextMessage(weChatMessage);
        //搜索文章
        /**
         * 微信最多回复1条图文消息
         * 见公告：https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&announce_id=115383153198yAvN&version=&lang=zh_CN&token=
         * */
        SearchDTO search = searchService.search(weChatMessage.getMessage(), "1", "1");
        if (search != null && search.getFullTextIndexDOS() != null && search.getFullTextIndexDOS().size() > 0) {
            //回复图文消息
            NewsMessage newsMessage = new NewsMessage(weChatMessage);
            newsMessage.setArticleCount(search.getFullTextIndexDOS().size() + "");
            List<ArticlesItem> item = new ArrayList<>();
            for (FullTextIndexDO indexDO : search.getFullTextIndexDOS()
            ) {
                ArticlesItem articlesItem = new ArticlesItem();
                articlesItem.setTitle(indexDO.getTitle());
                String desc = stringUtil.getTextFromHtml(indexDO.getContent());
                articlesItem.setDescription(sub(desc));
                articlesItem.setPicUrl(getPicUrl(indexDO));
                articlesItem.setUrl(globalService.getDomain() + indexDO.getUriPath() + "/" + indexDO.getId());
                item.add(articlesItem);
            }
            newsMessage.setArticles(item);
            return weChatMessageService.newsMessageToXml(newsMessage);
        } else {
            textMessage.setContent("我们已经收到您发送的消息，但在「RENFEI.NET」全站搜索中未能匹配到任何信息，请尝试更换关键字尝试。");
            return weChatMessageService.textMessageToXml(textMessage);
        }
    }

    /**
     * 极速下载服务
     *
     * @param weChatMessage
     * @return
     */
    private String JiSuDownload(WeChatMessage weChatMessage) {
        // 默认回复文本消息
        TextMessage textMessage = new TextMessage(weChatMessage);
        if (weChatMessage.getMessage() != null) {
            if (weChatMessage.getMessage().startsWith("极速下载") || weChatMessage.getMessage().startsWith("極速下載")) {
                String fileid = weChatMessage.getMessage().replace("极速下载", "").replace("極速下載", "");
                //去查询下载资源列表，生成随机码，记录到cache中
                DownloadDO downloadDO = downloadService.getDownloadInfoById(fileid);
                if (downloadDO != null) {
                    String key = cacheService.getRandomKey();
                    //过期时间十分钟，10*60*1000=600000
                    cacheService.set(key, JSON.toJSONString(downloadDO), new Date(new Date().getTime() + 600000));
                    textMessage.setContent("您的极速下载授权码是：" + key + "。有效期10分钟，请尽快使用。");
                    return weChatMessageService.textMessageToXml(textMessage);
                } else {
                    textMessage.setContent("您请求的极速下载文件编号不存在，请重新查证后重试。");
                    return weChatMessageService.textMessageToXml(textMessage);
                }
            }
        }
        return null;
    }

    private String sub(String msg) {
        if (msg != null && msg.length() > 120) {
            return msg.substring(0, 119);
        }
        return msg;
    }

    private WeChatKeywordDO getWeChatKeyword(String keyword) {
        WeChatKeywordDOExample weChatKeywordDOExample = new WeChatKeywordDOExample();
        weChatKeywordDOExample.createCriteria()
                .andKeywordEqualTo(keyword);
        List<WeChatKeywordDO> weChatKeywordDOS = weChatKeywordDOMapper.selectByExample(weChatKeywordDOExample);
        if (weChatKeywordDOS != null && weChatKeywordDOS.size() > 0) {
            return weChatKeywordDOS.get(0);
        }
        return null;
    }

    private String getPicUrl(FullTextIndexDO index) {
        if (index.getTypeID() == 1) {
            //Posts
            PostsDTO postsDTO = postsService.getPostsByID(index.getId() + "", false);
            return checkUrlHttp(postsDTO.getFeaturedImage());
        } else if (index.getTypeID() == 3) {
            //Video
            VideoDTO videoDTO = videoService.getVideoByID(index.getId() + "", false);
            return checkUrlHttp(videoDTO.getFeaturedImage());
        } else if (index.getTypeID() == 4) {
            //Photo
            PhotoDTO photoDTO = photoService.getPhotoById(index.getId() + "");
            return checkUrlHttp(photoDTO.getFeaturedImage());
        } else {
            return "https://cdn.renfei.net/logo/default-img.jpg";
        }
    }

    private String checkUrlHttp(String url) {
        if (url != null && url.length() > 0) {
            if (!url.startsWith("http")) {
                return "https:" + url;
            }
        }
        return url;
    }
}
