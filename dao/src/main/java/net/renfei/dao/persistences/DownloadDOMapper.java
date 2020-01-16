package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.DownloadDO;
import net.renfei.dao.entity.DownloadDOExample;
import org.apache.ibatis.annotations.Param;

public interface DownloadDOMapper {
    long countByExample(DownloadDOExample example);

    int deleteByExample(DownloadDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DownloadDO record);

    int insertSelective(DownloadDO record);

    List<DownloadDO> selectByExample(DownloadDOExample example);

    DownloadDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DownloadDO record, @Param("example") DownloadDOExample example);

    int updateByExample(@Param("record") DownloadDO record, @Param("example") DownloadDOExample example);

    int updateByPrimaryKeySelective(DownloadDO record);

    int updateByPrimaryKey(DownloadDO record);
}