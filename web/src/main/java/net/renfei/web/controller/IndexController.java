package net.renfei.web.controller;

import net.renfei.core.entity.*;
import net.renfei.dao.entity.VAllInfoWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.AllInfoVO;
import net.renfei.web.entity.CategoryVO;
import net.renfei.web.entity.OGprotocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class IndexController extends BaseController {
    @Value("classpath:xml/sitemap.xsl")
    private Resource sitemapxslXml;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView mv,
                              @RequestParam(value = "page", required = false) String page,
                              @RequestParam(value = "rows", required = false) String rows) {
        AllInfoDTOList allInfoDTOList = indexService.getAllInfo(page, rows);
        int intPage = indexService.convertPage(page);
        if (allInfoDTOList.getCount() > 0) {
            List<AllInfoVO> allInfoVOList = new ArrayList<>();
            for (VAllInfoWithBLOBs vAllInfoWithBLOBs : allInfoDTOList.getVAllInfoWithBLOBsList()
            ) {
                AllInfoVO obj = ejbGenerator.convert(vAllInfoWithBLOBs, AllInfoVO.class);
                if (stringUtil.isEmpty(obj.getDescribes())) {
                    obj.setDescribes(stringUtil.getTextFromHtml(obj.getTitle()));
                }
                obj.setHref(getUrl(obj.getTypeId(), obj.getId()));
                obj.setCatHref(geCattUrl(obj.getTypeId(), obj.getCatEname()));
                allInfoVOList.add(obj);
            }
            mv.addObject("allInfoVOList", allInfoVOList);
            mv.addObject("count", allInfoDTOList.getCount());
        }
        List<TypeDTO> typeDTOS = typeService.getAllType();
        mv.addObject("typeDTOS", typeDTOS);
        List<CategoryDTO> categoryDTOS = categorService.getAllCategory();
        if (categoryDTOS != null && categoryDTOS.size() > 0) {
            List<CategoryVO> categoryVOS = new ArrayList<>();
            for (CategoryDTO cat : categoryDTOS
            ) {
                CategoryVO categoryVO = ejbGenerator.convert(cat, CategoryVO.class);
                categoryVO.setHref(domain + "/cat" + categoryVO.getUriPath() + "/" + categoryVO.getEnName());
                categoryVOS.add(categoryVO);
            }
            mv.addObject("categories", categoryVOS);
        }
        LinkDTO linkDTO = linkService.getLinks();
        mv.addObject("linkDTO", linkDTO);
        mv.addObject("page", intPage);
        mv.addObject("homebanner", globalService.getHomeBanner());
        OGprotocol opg = new OGprotocol();
        opg.setType("blog");
        opg.setAuthor("任霏");
        opg.setDescription("这是 任霏 的个人网站与博客，一个程序员自己写的网站，不仅仅是文章内容，还包括网站程序的代码。 对新鲜事物都十分感兴趣，利用这个站点向大家分享自己的所见所得，同时这个站点也是我的实验室。");
        opg.setImage("https://cdn.renfei.net/logo/ogimage.png");
        opg.setLocale("zh-CN");
        opg.setReleaseDate(new Date());
        opg.setSiteName("RenFei.Net");
        opg.setTitle("任霏博客 - The RenFei Blog");
        opg.setUrl("https://www.renfei.net");
        setHead(mv, "任霏博客",
                "这是 任霏 的个人网站与博客，一个程序员自己写的网站，不仅仅是文章内容，还包括网站程序的代码。 对新鲜事物都十分感兴趣，利用这个站点向大家分享自己的所见所得，同时这个站点也是我的实验室。",
                "任霏,RenFei,NeilRen,博客,开发者,程序猿,程序媛,极客,编程,代码,开源,IT网站,Developer,Programmer,Coder,Geek,技术,blog", opg);
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(value = "robots.txt", produces = "text/plain")
    @ResponseBody
    public String getRobotsTxt() {
        String robots = "#\n" +
                "# robots.txt for RENFEI.NET\n" +
                "# Version 1.0.0\n" +
                "#\n" +
                "\n" +
                "User-agent: *\n" +
                "\n" +
                "Disallow: /search/\n" +
                "\n" +
                "Sitemap: " + domain + "/sitemap.xml";
        return robots;
    }

    @RequestMapping(value = "ads.txt", produces = "text/plain")
    @ResponseBody
    public String getGoogleAds() throws NoHandlerFoundException {
        String ads;
        ads = renFeiConfig.getGOOGLE_ADS();
        if (ads == null || ads.length() == 0) {
            throwNoHandlerFoundException();
        }
        return ads;
    }

    @RequestMapping(value = "sitemap.xml", produces = "text/xml;charset=UTF-8")
    @ResponseBody
    public String getSiteMapXml() throws ParseException {
        List<SiteMapXml> siteMapXmls = siteMapService.getSiteMaps();
        String xml = "    <url>\n" +
                "        <loc>%s</loc>\n" +
                "        <changefreq>%s</changefreq>\n" +
                "        <priority>%s</priority>\n" +
                "        <lastmod>%s</lastmod>\n" +
                "    </url>\n";
        StringBuffer resxml = new StringBuffer();
        resxml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        resxml.append("<?xml-stylesheet type=\"text/xsl\" href=\"" + domain + "/sitemap.xsl\"?>\n");
        resxml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
        for (SiteMapXml s : siteMapXmls) {
            resxml.append(String.format(xml, s.getLoc(), s.getChangefreq(), s.getPriority(), s.getLastmod()));
        }
        resxml.append("</urlset>\n");
        return resxml.toString();
    }

    @RequestMapping(value = "sitemap.xsl")
    @ResponseBody
    public String getSiteMapXsl(HttpServletResponse response) throws IOException {
        String sitemapxsl = IOUtils.toString(sitemapxslXml.getInputStream());
        response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        response.setContentType("application/octet-stream;charset=UTF-8");
        return sitemapxsl;
    }
}
