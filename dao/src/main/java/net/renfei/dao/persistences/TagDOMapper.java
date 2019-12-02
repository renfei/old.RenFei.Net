package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.TagDO;
import net.renfei.dao.entity.TagDOExample;
import net.renfei.dao.entity.TagDOExtend;
import org.apache.ibatis.annotations.Param;

public interface TagDOMapper {
    long countByExample(TagDOExample example);

    int deleteByExample(TagDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TagDO record);

    int insertSelective(TagDO record);

    List<TagDO> selectByExampleWithBLOBs(TagDOExample example);

    List<TagDO> selectByExample(TagDOExample example);

    TagDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TagDO record, @Param("example") TagDOExample example);

    int updateByExampleWithBLOBs(@Param("record") TagDO record, @Param("example") TagDOExample example);

    int updateByExample(@Param("record") TagDO record, @Param("example") TagDOExample example);

    int updateByPrimaryKeySelective(TagDO record);

    int updateByPrimaryKeyWithBLOBs(TagDO record);

    int updateByPrimaryKey(TagDO record);

    List<TagDOExtend> getAllTag();
}