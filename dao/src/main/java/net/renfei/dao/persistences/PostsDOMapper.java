package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.PostsDO;
import net.renfei.dao.entity.PostsDOExample;
import net.renfei.dao.entity.PostsDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface PostsDOMapper {
    long countByExample(PostsDOExample example);

    int deleteByExample(PostsDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PostsDOWithBLOBs record);

    int insertSelective(PostsDOWithBLOBs record);

    List<PostsDOWithBLOBs> selectByExampleWithBLOBs(PostsDOExample example);

    List<PostsDO> selectByExample(PostsDOExample example);

    PostsDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PostsDOWithBLOBs record, @Param("example") PostsDOExample example);

    int updateByExampleWithBLOBs(@Param("record") PostsDOWithBLOBs record, @Param("example") PostsDOExample example);

    int updateByExample(@Param("record") PostsDO record, @Param("example") PostsDOExample example);

    int updateByPrimaryKeySelective(PostsDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PostsDOWithBLOBs record);

    int updateByPrimaryKey(PostsDO record);
}