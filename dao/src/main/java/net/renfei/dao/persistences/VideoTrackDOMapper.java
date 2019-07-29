package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.VideoTrackDO;
import net.renfei.dao.entity.VideoTrackDOExample;
import net.renfei.dao.entity.VideoTrackDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface VideoTrackDOMapper {
    long countByExample(VideoTrackDOExample example);

    int deleteByExample(VideoTrackDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoTrackDOWithBLOBs record);

    int insertSelective(VideoTrackDOWithBLOBs record);

    List<VideoTrackDOWithBLOBs> selectByExampleWithBLOBs(VideoTrackDOExample example);

    List<VideoTrackDO> selectByExample(VideoTrackDOExample example);

    VideoTrackDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoTrackDOWithBLOBs record, @Param("example") VideoTrackDOExample example);

    int updateByExampleWithBLOBs(@Param("record") VideoTrackDOWithBLOBs record, @Param("example") VideoTrackDOExample example);

    int updateByExample(@Param("record") VideoTrackDO record, @Param("example") VideoTrackDOExample example);

    int updateByPrimaryKeySelective(VideoTrackDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(VideoTrackDOWithBLOBs record);

    int updateByPrimaryKey(VideoTrackDO record);
}