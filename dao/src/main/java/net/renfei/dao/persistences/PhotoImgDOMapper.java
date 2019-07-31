package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.PhotoImgDO;
import net.renfei.dao.entity.PhotoImgDOExample;
import org.apache.ibatis.annotations.Param;

public interface PhotoImgDOMapper {
    long countByExample(PhotoImgDOExample example);

    int deleteByExample(PhotoImgDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PhotoImgDO record);

    int insertSelective(PhotoImgDO record);

    List<PhotoImgDO> selectByExampleWithBLOBs(PhotoImgDOExample example);

    List<PhotoImgDO> selectByExample(PhotoImgDOExample example);

    PhotoImgDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PhotoImgDO record, @Param("example") PhotoImgDOExample example);

    int updateByExampleWithBLOBs(@Param("record") PhotoImgDO record, @Param("example") PhotoImgDOExample example);

    int updateByExample(@Param("record") PhotoImgDO record, @Param("example") PhotoImgDOExample example);

    int updateByPrimaryKeySelective(PhotoImgDO record);

    int updateByPrimaryKeyWithBLOBs(PhotoImgDO record);

    int updateByPrimaryKey(PhotoImgDO record);
}