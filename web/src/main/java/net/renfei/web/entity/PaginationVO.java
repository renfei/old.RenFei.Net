package net.renfei.web.entity;

import lombok.Data;

@Data
public class PaginationVO {
    private String link;
    private String page;
    private boolean active;
}
