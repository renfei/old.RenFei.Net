package net.renfei.dao.entity;

public class LinkDOWithBLOBs extends LinkDO {
    private String sitename;

    private String sitelink;

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename == null ? null : sitename.trim();
    }

    public String getSitelink() {
        return sitelink;
    }

    public void setSitelink(String sitelink) {
        this.sitelink = sitelink == null ? null : sitelink.trim();
    }
}