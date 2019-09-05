package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.SysMenuDO;
import net.renfei.dao.entity.SysMenuDOExample;
import net.renfei.dao.entity.SysMenuDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface SysMenuDOMapper {
    long countByExample(SysMenuDOExample example);

    int deleteByExample(SysMenuDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysMenuDOWithBLOBs record);

    int insertSelective(SysMenuDOWithBLOBs record);

    List<SysMenuDOWithBLOBs> selectByExampleWithBLOBs(SysMenuDOExample example);

    List<SysMenuDO> selectByExample(SysMenuDOExample example);

    SysMenuDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysMenuDOWithBLOBs record, @Param("example") SysMenuDOExample example);

    int updateByExampleWithBLOBs(@Param("record") SysMenuDOWithBLOBs record, @Param("example") SysMenuDOExample example);

    int updateByExample(@Param("record") SysMenuDO record, @Param("example") SysMenuDOExample example);

    int updateByPrimaryKeySelective(SysMenuDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysMenuDOWithBLOBs record);

    int updateByPrimaryKey(SysMenuDO record);
}