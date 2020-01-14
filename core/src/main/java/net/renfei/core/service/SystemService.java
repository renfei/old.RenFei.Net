package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.persistences.SettingDOMapper;
import net.renfei.dao.entity.SettingDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemService extends BaseService {
    private static String SITENAME = "sitename";
    private static String SITELOGO = "sitelogo";
    private static String DOMAIN = "domain";
    private static String HOMEBANNER = "homebanner";
    private static String STATICDOMAIN = "staticdomain";
    private static String GLOBALCSS = "css";
    private static String GLOBALJSS = "jss";
    private static String DESCRIPTION = "description";
    private static String SCRIPT = "script";
    private static String ANALYTICSCODE = "analyticscode";
    private static String GLOBALCOMMENT = "global_comment";
    private static String GLOBALAD = "global_ad";

    public String getSiteName() {
        return getValue(SITENAME);
    }

    public String getSiteLogo() {
        return getValue(SITELOGO);
    }

    public String getDomain() {
        return getValue(DOMAIN);
    }

    public String getHomeBanner() {
        return getValue(HOMEBANNER);
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

    public String getGlobalComment() {
        return getValue(GLOBALCOMMENT);
    }

    public String getGlobalAD() {
        return getValue(GLOBALAD);
    }

    private List<String> getCJss(String key) {
        List<SettingDOWithBLOBs> settingDOWithBLOBs = getSetting(key);
        if (settingDOWithBLOBs != null && settingDOWithBLOBs.size() > 0) {
            String staticDomain = getStaticDomain();
            List<String> css = new ArrayList<>();
            for (SettingDOWithBLOBs setting : settingDOWithBLOBs) {
                if (setting.getValues().startsWith("//") || setting.getValues().startsWith("http")) {
                    css.add(setting.getValues());
                } else {
                    css.add("//" + staticDomain + setting.getValues());
                }
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
