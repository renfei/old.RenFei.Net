package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.PostsDOWithBLOBs;

import java.util.List;

@Data
public class PostsListDTO {
    private Long count;
    private List<PostsDOWithBLOBs> postsList;
}
