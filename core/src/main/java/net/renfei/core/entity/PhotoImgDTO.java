package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.PhotoImgDO;

import java.util.List;

@Data
public class PhotoImgDTO {
    List<PhotoImgDO> photoImgs;
}
