package net.renfei.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.renfei.core.task.UpdatePostPageRankJob;
import net.renfei.dao.entity.LinkDOWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.APIResult;
import net.renfei.web.entity.FriendLinkVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;

@Slf4j
@Controller
@RequestMapping("/other")
public class OtherController extends BaseController {

    /**
     * 链接重定向（注意地址需要Base64编码以后传入）
     *
     * @param url URL需要Base64编码
     * @param mv
     * @return
     */
    @RequestMapping("urlredirect")
    public ModelAndView urlredirect(String url, ModelAndView mv) {
        if (stringUtil.isEmpty(url)) {
            return new ModelAndView("redirect:/");
        }
        try {
            byte[] asBytes = Base64.getDecoder().decode(url);
            mv.addObject("siteName", siteName);
            String uri = new String(asBytes, "utf-8");
            if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
                uri = "http://" + uri;
            }
            if (uri.startsWith(domain)) {
                return new ModelAndView("redirect:" + uri);
            }
            mv.addObject("url", uri);
            setHead(mv, "提示！Notice!");
            mv.setViewName("other/urlredirect");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ModelAndView("redirect:/");
        }
        return mv;
    }

    /**
     * 友情链接申请
     *
     * @param mv
     * @return
     */
    @RequestMapping("friendlink")
    public ModelAndView friendlink(ModelAndView mv) {
        setHead(mv, "友情链接申请",
                "为了与更多建站伙伴互相提高权重和品牌,特开放友情链接申请。您可以使用友情链接申请功能向我们提交友情链接交换的请求,我们会立即审核您的申请。",
                "友情链接,友情,链接,在线,申请");
        mv.setViewName("other/friendlink");
        return mv;
    }

    @PostMapping("friendlink/submit")
    @ResponseBody
    public APIResult friendlink(FriendLinkVO friendLinkVO) {
        APIResult apiResult = ejbGenerator.convert(linkService.addFriendLink(ejbGenerator.convert(friendLinkVO, LinkDOWithBLOBs.class)), APIResult.class);
        if (apiResult.getSuccess()) {
            linkService.sendNotify(ejbGenerator.convert(friendLinkVO, LinkDOWithBLOBs.class));
        }
        return apiResult;
    }
}
