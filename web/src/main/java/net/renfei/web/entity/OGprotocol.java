package net.renfei.web.entity;

import net.renfei.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Open Graph protocol
 */
public class OGprotocol {
    private String title;
    private String description;
    private String type;
    private String image;
    private String url;
    private Date releaseDate;
    private String author;
    private String locale;
    private String siteName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image == null ? "https://cdn.renfei.net/logo/ogimage.png" : image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getReleaseDate() {
        //此处要输出UTC标准时间
        DateUtil dateUtil = new DateUtil();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateUtil.localToUTC(sdf.format(releaseDate));
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
