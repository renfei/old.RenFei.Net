package net.renfei.www.entity.dto;

import lombok.Data;
import net.renfei.www.entity.dbo.VAllInfoWithBLOBs;

import java.util.List;

@Data
public class AllInfoDTOList {
    private Long count;
    private List<VAllInfoWithBLOBs> vAllInfoWithBLOBsList;
}
