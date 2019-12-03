package net.renfei.dao.entity;

public class LibraryDetailsDO {
    private Long id;

    private Long libraryId;

    private String fileName;

    private String lang;

    private String postDateString;

    private String sha1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang == null ? null : lang.trim();
    }

    public String getPostDateString() {
        return postDateString;
    }

    public void setPostDateString(String postDateString) {
        this.postDateString = postDateString == null ? null : postDateString.trim();
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1 == null ? null : sha1.trim();
    }
}