package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.VideoDO;
import net.renfei.dao.entity.VideoDOExample;
import net.renfei.dao.entity.VideoDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface VideoDOMapper {
    long countByExample(VideoDOExample example);

    int deleteByExample(VideoDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(VideoDOWithBLOBs record);

    int insertSelective(VideoDOWithBLOBs record);

    List<VideoDOWithBLOBs> selectByExampleWithBLOBs(VideoDOExample example);

    List<VideoDO> selectByExample(VideoDOExample example);

    VideoDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") VideoDOWithBLOBs record, @Param("example") VideoDOExample example);

    int updateByExampleWithBLOBs(@Param("record") VideoDOWithBLOBs record, @Param("example") VideoDOExample example);

    int updateByExample(@Param("record") VideoDO record, @Param("example") VideoDOExample example);

    int updateByPrimaryKeySelective(VideoDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(VideoDOWithBLOBs record);

    int updateByPrimaryKey(VideoDO record);
}