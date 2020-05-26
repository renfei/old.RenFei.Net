package net.renfei.web.entity;

import lombok.Data;
import net.renfei.dao.entity.TagDOExtend;

import java.util.List;

@Data
public class SidebarVO {
    private ITellYou iTellYou;
    /**
     * 文章分类
     */
    private List<CategoriesVo> categories;
    /**
     * 热文排行
     */
    private List<LinkVO> hotPost;
    /**
     * 年度热文排行
     */
    private List<LinkVO> hotPostYear;
    /**
     * 季度热文排行
     */
    private List<LinkVO> hotPostQuarter;
    /**
     * 热评文章
     */
    private List<LinkVO> hotComments;
    /**
     * 最新留言
     */
    private List<LinkVO> latestComments;
    /**
     * 评分最高文章
     */
    private List<LinkVO> topRated;
    private List<TagDOExtend> tags;
    private String staticdomain;
}
