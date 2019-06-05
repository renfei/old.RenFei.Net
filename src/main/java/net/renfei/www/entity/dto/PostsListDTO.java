package net.renfei.www.entity.dto;

import lombok.Data;
import net.renfei.www.entity.dbo.PostsDOWithBLOBs;

import java.util.List;

@Data
public class PostsListDTO {
    private Long count;
    private List<PostsDOWithBLOBs> postsList;
}
