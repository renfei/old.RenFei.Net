package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.LogDO;
import net.renfei.dao.entity.LogDOExample;
import org.apache.ibatis.annotations.Param;

public interface LogDOMapper {
    long countByExample(LogDOExample example);

    int deleteByExample(LogDOExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(LogDO record);

    int insertSelective(LogDO record);

    List<LogDO> selectByExampleWithBLOBs(LogDOExample example);

    List<LogDO> selectByExample(LogDOExample example);

    LogDO selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") LogDO record, @Param("example") LogDOExample example);

    int updateByExampleWithBLOBs(@Param("record") LogDO record, @Param("example") LogDOExample example);

    int updateByExample(@Param("record") LogDO record, @Param("example") LogDOExample example);

    int updateByPrimaryKeySelective(LogDO record);

    int updateByPrimaryKeyWithBLOBs(LogDO record);

    int updateByPrimaryKey(LogDO record);
}