package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.LibraryDO;
import net.renfei.dao.entity.LibraryDOExample;
import org.apache.ibatis.annotations.Param;

public interface LibraryDOMapper {
    long countByExample(LibraryDOExample example);

    int deleteByExample(LibraryDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LibraryDO record);

    int insertSelective(LibraryDO record);

    List<LibraryDO> selectByExample(LibraryDOExample example);

    LibraryDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LibraryDO record, @Param("example") LibraryDOExample example);

    int updateByExample(@Param("record") LibraryDO record, @Param("example") LibraryDOExample example);

    int updateByPrimaryKeySelective(LibraryDO record);

    int updateByPrimaryKey(LibraryDO record);
}