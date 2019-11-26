package net.renfei.web.controller;

import net.renfei.core.entity.IPDTO;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.KitboxVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/kitbox")
public class KitboxController extends BaseController {
    @RequestMapping("")
    public ModelAndView getKitboxIndex(ModelAndView mv) {
        List<KitboxVO> kitboxVOList = new ArrayList<>();
        kitboxVOList.add(new KitboxVO("fa-globe", "IP地址信息查询",
                "IP地址信息查询工具，开放服务接口实现IP信息查询", domain + "/kitbox/ip"));
        kitboxVOList.add(new KitboxVO("fa-globe", "Dig+trace命令工具",
                "域名解析查询工具，开放服务接口实现dig+trace查询", domain + "/kitbox/digtrace"));
        kitboxVOList.add(new KitboxVO("fa-globe", "UUID/GUID生成工具",
                "在线批量生成 UUID/GUID 工具，支持大小写连词符", domain + "/kitbox/uuid"));
        mv.addObject("kitboxVOList", kitboxVOList);
        setHead(mv, "开发者工具箱 - Kitbox", "免费的开发者工具箱小工具，工欲善其事，必先利其器。");
        mv.setViewName("kitbox/index");
        return mv;
    }

    @RequestMapping("ip")
    public ModelAndView getIPInfo(ModelAndView mv) {
        String myip = ipService.getIpAddr(localRequest.get());
        IPDTO ipdto = ipService.getIPInfor(myip);
        mv.addObject("myip", myip);
        mv.addObject("ipdto", ipdto);
        setHead(mv, "IP地址信息查询工具 - 开发者工具箱 - Kitbox", "IP地址信息查询工具，开放服务接口实现IP信息查询");
        mv.setViewName("kitbox/ipinfo");
        return mv;
    }

    @RequestMapping("digtrace")
    public ModelAndView getDigTrace(ModelAndView mv) {
        setHead(mv, "在线Dig+trace命令检测DNS状态工具 - 开发者工具箱 - Kitbox", "域名解析信息查询工具，开放服务接口实现dig+trace域名解析过程查询");
        mv.setViewName("kitbox/digtrace");
        return mv;
    }

    @RequestMapping({"uuid", "guid", "UUID", "GUID"})
    public ModelAndView getUUID(ModelAndView mv) {
        setHead(mv, "UUID/GUID 在线批量生成工具 - 开发者工具箱 - Kitbox", "在线批量生成 UUID/GUID 工具");
        mv.setViewName("kitbox/uuid");
        return mv;
    }
}
