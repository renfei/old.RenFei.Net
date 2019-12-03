package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.MovieDO;
import net.renfei.dao.entity.MovieDOExample;
import net.renfei.dao.entity.MovieDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface MovieDOMapper {
    long countByExample(MovieDOExample example);

    int deleteByExample(MovieDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MovieDOWithBLOBs record);

    int insertSelective(MovieDOWithBLOBs record);

    List<MovieDOWithBLOBs> selectByExampleWithBLOBs(MovieDOExample example);

    List<MovieDO> selectByExample(MovieDOExample example);

    MovieDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MovieDOWithBLOBs record, @Param("example") MovieDOExample example);

    int updateByExampleWithBLOBs(@Param("record") MovieDOWithBLOBs record, @Param("example") MovieDOExample example);

    int updateByExample(@Param("record") MovieDO record, @Param("example") MovieDOExample example);

    int updateByPrimaryKeySelective(MovieDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MovieDOWithBLOBs record);

    int updateByPrimaryKey(MovieDO record);
}