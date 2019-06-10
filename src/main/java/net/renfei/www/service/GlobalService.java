package net.renfei.www.service;

import net.renfei.www.common.baseclass.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GlobalService extends BaseService {
    @Autowired
    private SystemService systemService;

    public String getSiteName() {
        return systemService.getSiteName();
    }

    public String getDomain() {
        return systemService.getDomain();
    }

    public String getStaticDomain() {
        return systemService.getStaticDomain();
    }

    public String getAnalyticsCode() {
        return systemService.getAnalyticsCode();
    }

    public String getDescription() {
        return systemService.getDescription();
    }

    public String getScript() {
        return systemService.getScript();
    }

    public List<Map> getFooterMenu() {
        List<String> strings = systemService.getFooterMenu();
        if (strings != null && strings.size() > 0) {
            List<Map> maps = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            for (String s : strings) {
                map = new HashMap<>();
                map.put("text", s.split("\\|")[0]);
                map.put("link", s.split("\\|")[1]);
                maps.add(map);
            }
            map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            map.put("text", "© RENFEI.NET " + cal.get(Calendar.YEAR));
            map.put("link", "javascript:void(0)");
            maps.add(map);
            return maps;
        } else {
            return null;
        }
    }

    public List<String> getGlobalCSSList() {
        return systemService.getCss();
    }

    public List<String> getGlobalJSsList() {
        return systemService.getJss();
    }
}
