package net.renfei.web.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SearchVO {
    private String searchWord;
    private List<Search> searchList;
    private Long results;
    private Double seconds;

    public static class Search {
        private String title;
        private String link;
        private Date date;
        private String describe;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
