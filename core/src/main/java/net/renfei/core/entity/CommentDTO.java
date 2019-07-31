package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.CommentDOWithBLOBs;

import java.util.List;

@Data
public class CommentDTO extends CommentDOWithBLOBs {
    private List<CommentDTO> child;
}
