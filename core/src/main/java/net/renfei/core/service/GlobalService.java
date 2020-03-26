package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
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

    public String getSiteLogo() {
        return systemService.getSiteLogo();
    }

    public String getDomain() {
        return systemService.getDomain();
    }

    public String getHomeBanner() {
        return systemService.getHomeBanner();
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

    public List<String> getGlobalCSSList() {
        return systemService.getCss();
    }

    public List<String> getGlobalJSsList(boolean isHead) {
        return systemService.getJss(isHead);
    }

    public String getGlobalComment(){return systemService.getGlobalComment();}

    public String getGlobalAD(){return systemService.getGlobalAD();}

    public String getStaticVersion(){return systemService.getStaticVersion();}
}
