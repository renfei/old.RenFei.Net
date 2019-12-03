package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.LibraryDetailsDO;
import net.renfei.dao.entity.LibraryDetailsDOExample;
import net.renfei.dao.entity.LibraryDetailsDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface LibraryDetailsDOMapper {
    long countByExample(LibraryDetailsDOExample example);

    int deleteByExample(LibraryDetailsDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LibraryDetailsDOWithBLOBs record);

    int insertSelective(LibraryDetailsDOWithBLOBs record);

    List<LibraryDetailsDOWithBLOBs> selectByExampleWithBLOBs(LibraryDetailsDOExample example);

    List<LibraryDetailsDO> selectByExample(LibraryDetailsDOExample example);

    LibraryDetailsDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LibraryDetailsDOWithBLOBs record, @Param("example") LibraryDetailsDOExample example);

    int updateByExampleWithBLOBs(@Param("record") LibraryDetailsDOWithBLOBs record, @Param("example") LibraryDetailsDOExample example);

    int updateByExample(@Param("record") LibraryDetailsDO record, @Param("example") LibraryDetailsDOExample example);

    int updateByPrimaryKeySelective(LibraryDetailsDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(LibraryDetailsDOWithBLOBs record);

    int updateByPrimaryKey(LibraryDetailsDO record);
}