package net.renfei.core.entity;

import lombok.Data;
import net.renfei.dao.entity.WeiboDOS;

import java.util.List;

@Data
public class WeiboDTO {
    private Long count;
    private List<WeiboDOS> weiboDOList;
}
