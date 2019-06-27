package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.CommentDO;
import net.renfei.dao.entity.CommentDOExample;
import net.renfei.dao.entity.CommentDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface CommentDOMapper {
    long countByExample(CommentDOExample example);

    int deleteByExample(CommentDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommentDOWithBLOBs record);

    int insertSelective(CommentDOWithBLOBs record);

    List<CommentDOWithBLOBs> selectByExampleWithBLOBs(CommentDOExample example);

    List<CommentDO> selectByExample(CommentDOExample example);

    CommentDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommentDOWithBLOBs record, @Param("example") CommentDOExample example);

    int updateByExampleWithBLOBs(@Param("record") CommentDOWithBLOBs record, @Param("example") CommentDOExample example);

    int updateByExample(@Param("record") CommentDO record, @Param("example") CommentDOExample example);

    int updateByPrimaryKeySelective(CommentDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CommentDOWithBLOBs record);

    int updateByPrimaryKey(CommentDO record);
}