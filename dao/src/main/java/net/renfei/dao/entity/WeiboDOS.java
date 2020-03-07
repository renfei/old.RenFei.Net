package net.renfei.dao.entity;

public class WeiboDOS extends WeiboDO {
    private String img;
    private Long comments;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }
}
