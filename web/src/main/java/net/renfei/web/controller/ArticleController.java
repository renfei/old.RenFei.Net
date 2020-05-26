package net.renfei.web.controller;

import net.renfei.web.baseclass.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: ArticleController</p>
 * <p>Description: Article是历史遗留，当初neilren.com使用过的路径，现在已经转移到/posts下</p>
 *
 * @author RenFei
 * @date : 2020-05-25 13:31
 */
@Controller
@RequestMapping({"/Article","/article"})
public class ArticleController extends BaseController {
    @RequestMapping("")
    public void article(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", domain + "/posts");
    }

    @RequestMapping("{id}")
    public void article(@PathVariable("id") String id, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", domain + "/posts/" + id);
    }
}
