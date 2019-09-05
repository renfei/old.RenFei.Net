package net.renfei.dao.entity;

public class SysMenuDOWithBLOBs extends SysMenuDO {
    private String menuName;

    private String menuLink;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink == null ? null : menuLink.trim();
    }
}