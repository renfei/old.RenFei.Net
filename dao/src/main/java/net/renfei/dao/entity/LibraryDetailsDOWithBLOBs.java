package net.renfei.dao.entity;

public class LibraryDetailsDOWithBLOBs extends LibraryDetailsDO {
    private String downLoad;

    private String size;

    public String getDownLoad() {
        return downLoad;
    }

    public void setDownLoad(String downLoad) {
        this.downLoad = downLoad == null ? null : downLoad.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }
}