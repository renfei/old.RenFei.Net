package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.VideoDOWithBLOBs;

import java.util.List;

@Data
public class VideoListDTO {
    private Long count;
    private List<VideoDOWithBLOBs> videos;
}
