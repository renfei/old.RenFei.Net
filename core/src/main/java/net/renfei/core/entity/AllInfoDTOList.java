package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.VAllInfoWithBLOBs;

import java.util.List;

@Data
public class AllInfoDTOList {
    private Long count;
    private List<VAllInfoWithBLOBs> vAllInfoWithBLOBsList;
}
