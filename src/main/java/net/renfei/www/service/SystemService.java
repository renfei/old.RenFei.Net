package net.renfei.www.service;

import net.renfei.www.common.baseclass.BaseService;
import net.renfei.www.dao.SettingDOMapper;
import net.renfei.www.entity.dbo.SettingDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService extends BaseService {
    private static String SITENAME = "sitename";
    private static String DOMAIN = "domain";
    private static String STATICDOMAIN = "staticdomain";
    private static String GLOBALCSS = "css";
    private static String GLOBALJSS = "jss";
    private static String DESCRIPTION = "description";
    private static String SCRIPT = "script";
    private static String ANALYTICSCODE = "analyticscode";
    private static String FOOTERMENU = "footermenu";
    @Autowired
    private SettingDOMapper settingDOMapper;

    public String getSiteName() {
        return getValue(SITENAME);
    }

    public String getDomain() {
        return getValue(DOMAIN);
    }

    public String getDescription() {
        return getValue(DESCRIPTION);
    }

    public String getScript() {
        return getValue(SCRIPT);
    }

    public String getAnalyticsCode() {
        return getValue(ANALYTICSCODE);
    }

    public String getStaticDomain() {
        return getValue(STATICDOMAIN);
    }

    public List<String> getCss() {
        return getCJss(GLOBALCSS);
    }

    public List<String> getJss() {
        return getCJss(GLOBALJSS);
    }

    public List<String> getFooterMenu() {
        return getValues(FOOTERMENU);
    }

    private List<String> getCJss(String key) {
        List<SettingDOWithBLOBs> settingDOWithBLOBs = getSetting(key);
        if (settingDOWithBLOBs != null && settingDOWithBLOBs.size() > 0) {
            String staticDomain = getStaticDomain();
            List<String> css = new ArrayList<>();
            for (SettingDOWithBLOBs setting : settingDOWithBLOBs) {
                css.add("//" + staticDomain + setting.getValues());
            }
            return css;
        } else {
            return null;
        }
    }

    private List<String> getValues(String key) {
        List<SettingDOWithBLOBs> settingDOWithBLOBs = getSetting(key);
        if (settingDOWithBLOBs != null && settingDOWithBLOBs.size() > 0) {
            List<String> css = new ArrayList<>();
            for (SettingDOWithBLOBs setting : settingDOWithBLOBs) {
                css.add(setting.getValues());
            }
            return css;
        } else {
            return null;
        }
    }

    public String getValue(String key) {
        List<SettingDOWithBLOBs> settingDOWithBLOBs = getSetting(key);
        if (settingDOWithBLOBs != null && settingDOWithBLOBs.size() > 0) {
            return settingDOWithBLOBs.get(0).getValues();
        } else {
            return null;
        }
    }

    public List<SettingDOWithBLOBs> getSetting(String key) {
        if (!stringUtil.isEmpty(key)) {
            return settingDOMapper.selectByKeys(key);
        } else {
            return null;
        }
    }
}