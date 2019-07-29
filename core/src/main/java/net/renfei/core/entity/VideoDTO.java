package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import net.renfei.dao.entity.VideoSourceDOWithBLOBs;
import net.renfei.dao.entity.VideoTrackDOWithBLOBs;

import java.util.List;

@Data
public class VideoDTO extends VideoDOWithBLOBs {
    private String categoryZhName;
    private String categoryEnName;
    private String categoryTypeName;
    private Long comments;
    List<VideoSourceDOWithBLOBs> videoSource;
    List<VideoTrackDOWithBLOBs> videoTrack;
}
