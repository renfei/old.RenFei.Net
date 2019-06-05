package net.renfei.www.service;

import net.renfei.www.common.baseclass.BaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GlobalService extends BaseService {
    public String getSiteName(){
        return "SiteName";
    }
    public List<String> getGlobalCSSList() {
        List<String> css = new ArrayList<>();
        css.add("//static.oncdn.cn/font/font-awesome/4.7.0/css/font-awesome.min.css");
        css.add("/css/style.css");
        return css;
    }

    public List<String> getGlobalJSsList() {
        List<String> jss = new ArrayList<>();
        jss.add("");
        return jss;
    }
}
