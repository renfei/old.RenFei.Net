package net.renfei.web.controller;

import net.renfei.core.entity.PostsDTO;
import net.renfei.core.entity.PostsListDTO;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.HeaderVO;
import net.renfei.web.entity.Menu2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/lianjiewenhua")
public class LianjieWenHuaController extends BaseController {
    @RequestMapping("")
    public ModelAndView index(ModelAndView mv) {
        return new ModelAndView("redirect:/lianjiewenhua/lianjiegushi");
    }

    @RequestMapping("lianjiegushi")
    public ModelAndView lianjiegushi(ModelAndView mv) {
        setHead(mv, "廉洁故事");
        setMenu(mv, "廉洁故事");
        PostsListDTO postsListDTO = postsService.getAllPostsByCatID(1L, "1", "999999");
        if (postsListDTO != null) {
            List<PostsDOWithBLOBs> postsDOWithBLOBs = postsListDTO.getPostsList();
            for (PostsDOWithBLOBs post : postsDOWithBLOBs
            ) {
                setPrize(post);
            }
            mv.addObject("postsDOWithBLOBs", postsDOWithBLOBs);
        }
        mv.addObject("url", "lianjiegushi");
        mv.setViewName("lianjiewenhua/shufalist");
        return mv;
    }

    @RequestMapping("lianjiegushi/{id}")
    public ModelAndView lianjiegushiCont(ModelAndView mv, @PathVariable("id") Long id) {
        PostsDTO postsDTO = postsService.getPostsByID(id.toString());
        if (postsDTO != null) {
            setHead(mv, postsDTO.getTitle() + " - 廉洁故事");
            setMenu(mv, "廉洁故事");
            setPrize(postsDTO);
            mv.addObject("post", postsDTO);
            mv.addObject("title2", "廉洁故事征文");
            mv.setViewName("lianjiewenhua/lianjie");
            return mv;
        } else return mv;
    }

    @RequestMapping("jiashujiaxun")
    public ModelAndView jiashujiaxun(ModelAndView mv) {
        setHead(mv, "家书家规家训");
        setMenu(mv, "家书家规家训");
        PostsListDTO postsListDTO = postsService.getAllPostsByCatID(2L, "1", "999999");
        if (postsListDTO != null) {
            List<PostsDOWithBLOBs> postsDOWithBLOBs = postsListDTO.getPostsList();
            for (PostsDOWithBLOBs post : postsDOWithBLOBs
            ) {
                setPrize(post);
            }
            mv.addObject("postsDOWithBLOBs", postsDOWithBLOBs);
        }
        mv.addObject("url", "jiashujiaxun");
        mv.setViewName("lianjiewenhua/shufalist");
        return mv;
    }

    @RequestMapping("jiashujiaxun/{id}")
    public ModelAndView jiashujiaxunCont(ModelAndView mv, @PathVariable("id") Long id) {
        PostsDTO postsDTO = postsService.getPostsByID(id.toString());
        if (postsDTO != null) {
            setHead(mv, postsDTO.getTitle() + " - 家书家规家训");
            setMenu(mv, "家书家规家训");
            setPrize(postsDTO);
            mv.addObject("post", postsDTO);
            mv.addObject("title2", "家书家规家训征文");
            mv.setViewName("lianjiewenhua/lianjie");
            return mv;
        } else return mv;
    }

    @RequestMapping("shufazuopin")
    public ModelAndView shufazuopin(ModelAndView mv) {
        setHead(mv, "书法作品");
        setMenu(mv, "书法作品");
        PostsListDTO postsListDTO = postsService.getAllPostsByCatID(3L, "1", "999999");
        if (postsListDTO != null) {
            List<PostsDOWithBLOBs> postsDOWithBLOBs = postsListDTO.getPostsList();
            for (PostsDOWithBLOBs post : postsDOWithBLOBs
            ) {
                setPrize(post);
            }
            mv.addObject("postsDOWithBLOBs", postsDOWithBLOBs);
        }
        mv.addObject("url", "shufazuopin");
        mv.addObject("lanmu", "书法作品");
        mv.setViewName("lianjiewenhua/shufalist");
        return mv;
    }

    @RequestMapping("shufazuopin/{id}")
    public ModelAndView shufazuopinCont(ModelAndView mv, @PathVariable("id") Long id) {
        PostsDTO postsDTO = postsService.getPostsByID(id.toString());
        if (postsDTO != null) {
            setHead(mv, postsDTO.getTitle() + " - 书法作品");
            setMenu(mv, "书法作品");
            setPrize(postsDTO);
            postsDTO.setContent("<img class=\"heng shufa\" src=\"/doc/书法作品/" + postsDTO.getContent() + "\" alt=\"\">");
            mv.addObject("post", postsDTO);
            mv.addObject("title2", "书法作品征文");
            mv.setViewName("lianjiewenhua/lianjie");
            return mv;
        } else return mv;
    }

    @RequestMapping("meishuzuopin")
    public ModelAndView meishuzuopin(ModelAndView mv) {
        setHead(mv, "美术作品");
        setMenu(mv, "美术作品");
        PostsListDTO postsListDTO = postsService.getAllPostsByCatID(4L, "1", "999999");
        if (postsListDTO != null) {
            List<PostsDOWithBLOBs> postsDOWithBLOBs = postsListDTO.getPostsList();
            for (PostsDOWithBLOBs post : postsDOWithBLOBs
            ) {
                setPrize(post);
            }
            mv.addObject("postsDOWithBLOBs", postsDOWithBLOBs);
        }
        mv.addObject("url", "meishuzuopin");
        mv.addObject("lanmu", "美术作品");
        mv.setViewName("lianjiewenhua/shufalist");
        return mv;
    }

    @RequestMapping("meishuzuopin/{id}")
    public ModelAndView meishuzuopinCont(ModelAndView mv, @PathVariable("id") Long id) {
        PostsDTO postsDTO = postsService.getPostsByID(id.toString());
        if (postsDTO != null) {
            setHead(mv, postsDTO.getTitle() + " - 美术作品");
            setMenu(mv, "美术作品");
            setPrize(postsDTO);
            postsDTO.setContent("<img class=\"heng shufa\" src=\"/doc/美术作品/" + postsDTO.getContent() + "\" alt=\"\">");
            mv.addObject("post", postsDTO);
            mv.addObject("title2", "美术作品征文");
            mv.setViewName("lianjiewenhua/lianjie");
            return mv;
        } else return mv;
    }

    @RequestMapping("weishipin")
    public ModelAndView weishipin(ModelAndView mv) {
        setHead(mv, "微视频(微电影)");
        setMenu(mv, "微视频(微电影)");
        PostsListDTO postsListDTO = postsService.getAllPostsByCatID(5L, "1", "999999");
        if (postsListDTO != null) {
            List<PostsDOWithBLOBs> postsDOWithBLOBs = postsListDTO.getPostsList();
            for (PostsDOWithBLOBs post : postsDOWithBLOBs
            ) {
                setPrize(post);
            }
            mv.addObject("postsDOWithBLOBs", postsDOWithBLOBs);
        }
        mv.addObject("url", "sheyingzuopin");
        mv.setViewName("lianjiewenhua/moving");
        return mv;
    }

    @RequestMapping("sheyingzuopin")
    public ModelAndView sheyingzuopin(ModelAndView mv) {
        setHead(mv, "摄影作品");
        setMenu(mv, "摄影作品");
        PostsListDTO postsListDTO = postsService.getAllPostsByCatID(6L, "1", "999999");
        if (postsListDTO != null) {
            List<PostsDOWithBLOBs> postsDOWithBLOBs = postsListDTO.getPostsList();
            for (PostsDOWithBLOBs post : postsDOWithBLOBs
            ) {
                setPrize(post);
            }
            mv.addObject("postsDOWithBLOBs", postsDOWithBLOBs);
        }
        mv.addObject("url", "sheyingzuopin");
        mv.setViewName("lianjiewenhua/sheyinglist");
        return mv;
    }

    private void setPrize(PostsDOWithBLOBs post) {
        if (post.getPrize() == null) {
            post.setPrizeName("未知");
        } else {
            switch (post.getPrize()) {
                case 0:
                    post.setPrizeName("优秀奖");
                    break;
                case 1:
                    post.setPrizeName("一等奖");
                    break;
                case 2:
                    post.setPrizeName("二等奖");
                    break;
                case 3:
                    post.setPrizeName("三等奖");
                    break;
                case 4:
                    post.setPrizeName("四等奖");
                    break;
                case 5:
                    post.setPrizeName("五等奖");
                    break;
                default:
                    post.setPrizeName("未知");
                    break;
            }
        }
    }

    private void setMenu(ModelAndView mv, String active) {
        ///HEADER_KEY
        Map<String, Object> map = mv.getModel();
        HeaderVO headerVO = new HeaderVO();
        Object obj = map.get(HEADER_KEY);
        if (obj instanceof HeaderVO) {
            headerVO = (HeaderVO) obj;
        }
        List<Menu2> menu2 = new ArrayList<>();
        Menu2 menu21 = new Menu2();
        menu21.setName("廉洁故事");
        menu21.setLink("/lianjiewenhua/lianjiegushi");
        menu2.add(menu21);
        menu21 = new Menu2();
        menu21.setName("家书家规家训");
        menu21.setLink("/lianjiewenhua/jiashujiaxun");
        menu2.add(menu21);
        menu21 = new Menu2();
        menu21.setName("书法作品");
        menu21.setLink("/lianjiewenhua/shufazuopin");
        menu2.add(menu21);
        menu21 = new Menu2();
        menu21.setName("美术作品");
        menu21.setLink("/lianjiewenhua/meishuzuopin");
        menu2.add(menu21);
        menu21 = new Menu2();
        menu21.setName("微视频(微电影)");
        menu21.setLink("/lianjiewenhua/weishipin");
        menu2.add(menu21);
        menu21 = new Menu2();
        menu21.setName("摄影作品");
        menu21.setLink("/lianjiewenhua/sheyingzuopin");
        menu2.add(menu21);
        for (Menu2 m : menu2
        ) {
            if (m.getName().equals(active)) {
                m.setActive(true);
            } else {
                m.setActive(false);
            }
        }
        headerVO.setMenu2(menu2);
        mv.addObject(HEADER_KEY, headerVO);
    }
}
