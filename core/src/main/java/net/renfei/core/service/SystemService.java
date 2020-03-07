package net.renfei.core.service;

import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.entity.SettingDOExample;
import net.renfei.dao.persistences.SettingDOMapper;
import net.renfei.dao.entity.SettingDOWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static String STATICVERSION = "staticversion";

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
        return getCss(GLOBALCSS);
    }

    public List<String> getJss(boolean isHead) {
        return getCJss(GLOBALJSS, isHead);
    }

    public String getGlobalComment() {
        return getValue(GLOBALCOMMENT);
    }

    public String getGlobalAD() {
        return getValue(GLOBALAD);
    }

    public String getStaticVersion() {
        return Optional.ofNullable(getValue(STATICVERSION)).orElse("");
    }

    private List<String> getCJss(String key, boolean isHead) {
        List<SettingDOWithBLOBs> settingDOWithBLOBs = getSetting(key, isHead ? "1" : null);
        if (settingDOWithBLOBs != null && settingDOWithBLOBs.size() > 0) {
            String staticVersion = getStaticVersion();
            String staticDomain = getStaticDomain();
            List<String> jss = new ArrayList<>();
            for (SettingDOWithBLOBs setting : settingDOWithBLOBs) {
                if (setting.getValues().startsWith("//") || setting.getValues().startsWith("http")) {
                    jss.add(setting.getValues() + staticVersion);
                } else {
                    jss.add("//" + staticDomain + setting.getValues() + staticVersion);
                }
            }
            return jss;
        } else {
            return null;
        }
    }

    private List<String> getCss(String key) {
        List<SettingDOWithBLOBs> settingDOWithBLOBs = getSetting(key);
        if (settingDOWithBLOBs != null && settingDOWithBLOBs.size() > 0) {
            String staticVersion = getStaticVersion();
            String staticDomain = getStaticDomain();
            List<String> css = new ArrayList<>();
            for (SettingDOWithBLOBs setting : settingDOWithBLOBs) {
                if (setting.getValues().startsWith("//") || setting.getValues().startsWith("http")) {
                    css.add(setting.getValues() + staticVersion);
                } else {
                    css.add("//" + staticDomain + setting.getValues() + staticVersion);
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

    public List<SettingDOWithBLOBs> getSetting(String key, String extend) {
        if (!stringUtil.isEmpty(key)) {
            if (extend == null) {
                return settingDOMapper.selectByKeysAndExtendNull(key);
            } else {
                return settingDOMapper.selectByKeysAndExtend(key, extend);
            }
        } else {
            return null;
        }
    }
}
