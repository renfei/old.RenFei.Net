package net.renfei.web.entity;

import lombok.Data;

@Data
public class CategoriesVo {
    private String categoriesName;
    private String link;
    private Long cnt;
    private boolean activ;
}
