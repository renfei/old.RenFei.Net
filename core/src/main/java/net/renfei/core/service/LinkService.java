package net.renfei.core.service;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.APIResult;
import net.renfei.core.entity.LinkDTO;
import net.renfei.dao.entity.LinkDOExample;
import net.renfei.dao.entity.LinkDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@CacheConfig(cacheNames = "LinkService")
public class LinkService extends BaseService {
    @Autowired
    private MailService mailService;

    @Cacheable(key = "targetClass+'_'+methodName")
    public LinkDTO getLinks() {
        LinkDOExample linkDOExample = new LinkDOExample();
        linkDOExample.setOrderByClause("order_id ASC");
        linkDOExample.createCriteria()
                .andAuditPassEqualTo(true)
                .andIsDeleteEqualTo(false);
        List<LinkDOWithBLOBs> linkDOWithBLOBs = linkDOMapper.selectByExampleWithBLOBs(linkDOExample);
        if (linkDOWithBLOBs != null && linkDOWithBLOBs.size() > 0) {
            LinkDTO linkDTO = new LinkDTO();
            linkDTO.setLinks(linkDOWithBLOBs);
            return linkDTO;
        } else {
            return null;
        }
    }

    public APIResult addFriendLink(LinkDOWithBLOBs linkDOWithBLOBs) {
        if (stringUtil.isEmpty(linkDOWithBLOBs.getSitename())) {
            return APIResult.fillResult(false, "网站名称是必填项！");
        }
        if (stringUtil.isEmpty(linkDOWithBLOBs.getSitelink())) {
            return APIResult.fillResult(false, "网站链接是必填项！");
        }
        if (linkDOWithBLOBs.getLinkType() == 2) {
            if (stringUtil.isEmpty(linkDOWithBLOBs.getInSiteLink())) {
                return APIResult.fillResult(false, "您选择了交叉交换类型，本站在贵站链接位置是必填项！");
            }
        }
        if (stringUtil.isEmpty(linkDOWithBLOBs.getContactName())) {
            return APIResult.fillResult(false, "您的称呼是必填项！");
        }
        if (stringUtil.isEmpty(linkDOWithBLOBs.getContactEmail())) {
            return APIResult.fillResult(false, "您的电子邮箱是必填项！");
        }
        linkDOWithBLOBs.setAddtime(new Date());
        linkDOWithBLOBs.setAuditPass(false);
        linkDOWithBLOBs.setIsDelete(false);
        linkDOWithBLOBs.setOrderId(999);
        try {
            linkDOMapper.insertSelective(linkDOWithBLOBs);
            return APIResult.fillResult(true, "提交成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return APIResult.fillResult(false, "抱歉，内部服务发生异常错误");
        }
    }

//    @Async
    public void sendNotify(LinkDOWithBLOBs linkDOWithBLOBs) {
        List<String> stringList = new ArrayList<>();
        stringList.add("您好！");
        stringList.add("很荣幸能与贵站【" + linkDOWithBLOBs.getSitename() + "】交换友情链接，我们已经收到了您的请求，请您耐心等待审核，无论成功与否我们都会给您邮件告知结果。");
        stringList.add("为了在审核时能在贵站上看到我站的链接，请您提前在贵站做上本站的友情链接。");
        stringList.add("----");
        stringList.add("友情链接申请系统");
        mailService.send(linkDOWithBLOBs.getContactEmail(), linkDOWithBLOBs.getContactName(), "我们已经收到您在[RENFEI.NET]提交的友情链接交换申请请求", stringList);
        mailService.send("i@renfei.net", "RenFei", "[RENFEI.NET]收到了新的友情链接交换申请请求", stringList);
    }
}
