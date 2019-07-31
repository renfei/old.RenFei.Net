package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.PhotoDOWithBLOBs;

import java.util.List;

@Data
public class PhotoListDTO {
    private Long count;
    private List<PhotoDOWithBLOBs> photoDOWithBLOBs;
}
