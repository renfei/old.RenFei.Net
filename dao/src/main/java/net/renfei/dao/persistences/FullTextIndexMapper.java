package net.renfei.dao.persistences;

import net.renfei.dao.entity.FullTextIndexDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FullTextIndexMapper {
    List<FullTextIndexDO> selectByWord(@Param("word") String word);
}
