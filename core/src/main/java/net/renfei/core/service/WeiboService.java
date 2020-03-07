package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.WeiboDTO;
import net.renfei.dao.entity.PhotoImgDO;
import net.renfei.dao.entity.WeiboDO;
import net.renfei.dao.entity.WeiboDOExample;
import net.renfei.dao.entity.WeiboDOS;
import net.renfei.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "WeiboService")
public class WeiboService extends BaseService {
    @Autowired
    private PhotoService photoService;
    @Autowired
    private CommentsService commentsService;

    /**
     * 获取微博列表
     *
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    public WeiboDTO getAllPosts(String pages, String rows) {
        return getAllPosts(pages, rows, "release_time DESC");
    }

    /**
     * 获取微博列表
     *
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    public WeiboDTO getAllPosts(String pages, String rows, String orderBy) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        WeiboDOExample weiboDOExample = new WeiboDOExample();
        weiboDOExample.setOrderByClause(orderBy);
        weiboDOExample
                .createCriteria()
                .andReleaseTimeLessThanOrEqualTo(new Date());
        return doSelect(weiboDOExample, intPage, intRows);
    }

    /**
     * 执行查询并转换
     *
     * @param weiboDOExample
     * @param ipage
     * @param rows
     * @return
     */
    private WeiboDTO doSelect(WeiboDOExample weiboDOExample, int ipage, int rows) {
        Page page = PageHelper.startPage(ipage, rows);
        List<WeiboDO> weiboDOList = weiboDOMapper.selectByExample(weiboDOExample);
        return convert(weiboDOList, page.getTotal());
    }

    public WeiboDO getWeiboById(String id, boolean recordViews) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                WeiboDOExample weiboDOExample = new WeiboDOExample();
                weiboDOExample.createCriteria()
                        .andIdEqualTo(ID)
                        .andReleaseTimeLessThanOrEqualTo(new Date());
                WeiboDO weiboDO = ListUtil.getOne(weiboDOMapper.selectByExample(weiboDOExample));
                if (weiboDO != null) {
                    if (recordViews) {
                        updateView(weiboDO);
                    }
                    return weiboDO;
                } else {
                    return null;
                }
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void thumbsUp(String id) {
        WeiboDO weiboDO = getWeiboById(id, false);
        if (weiboDO != null) {
            weiboDO.setThumbsUp(weiboDO.getThumbsUp() + 1);
            update(weiboDO);
        }
    }

    /**
     * 点踩
     *
     * @param id
     */
    public void thumbsDown(String id) {
        WeiboDO weiboDO = getWeiboById(id, false);
        if (weiboDO != null) {
            weiboDO.setThumbsDown(weiboDO.getThumbsDown() + 1);
            update(weiboDO);
        }
    }

    @Async
    public void updateView(WeiboDO weiboDO) {
        weiboDO.setViews(weiboDO.getViews() + 1);
        update(weiboDO);
    }

    public void update(WeiboDO weiboDO) {
        weiboDOMapper.updateByPrimaryKeySelective(weiboDO);
    }

    private WeiboDTO convert(List<WeiboDO> weiboDOList, Long count) {
        WeiboDTO weiboDTO = new WeiboDTO();
        List<WeiboDOS> weiboDOSList = new ArrayList<>();
        for (WeiboDO weibo : weiboDOList
        ) {
            WeiboDOS weiboDOS = new WeiboDOS();
            weiboDOS = ejbGenerator.convert(weibo, WeiboDOS.class);
            setImg(weiboDOS);
            weiboDOS.setComments(commentsService.getCommentNumber(6L, weibo.getId()));
            updateView(weibo);
            weiboDOSList.add(weiboDOS);
        }
        weiboDTO.setWeiboDOList(weiboDOSList);
        weiboDTO.setCount(count);
        return weiboDTO;
    }

    public void setImg(WeiboDOS weiboDOS) {
        if (weiboDOS.getImgId() != null) {
            PhotoImgDO photoImgDO = photoService.getPhotoImgById(weiboDOS.getImgId().toString());
            if (photoImgDO != null) {
                weiboDOS.setImg(photoImgDO.getUri());
            }
        }
    }
}
