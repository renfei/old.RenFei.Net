package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.PermissionRoleDO;
import net.renfei.dao.entity.PermissionRoleDOExample;
import org.apache.ibatis.annotations.Param;

public interface PermissionRoleDOMapper {
    long countByExample(PermissionRoleDOExample example);

    int deleteByExample(PermissionRoleDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PermissionRoleDO record);

    int insertSelective(PermissionRoleDO record);

    List<PermissionRoleDO> selectByExample(PermissionRoleDOExample example);

    PermissionRoleDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PermissionRoleDO record, @Param("example") PermissionRoleDOExample example);

    int updateByExample(@Param("record") PermissionRoleDO record, @Param("example") PermissionRoleDOExample example);

    int updateByPrimaryKeySelective(PermissionRoleDO record);

    int updateByPrimaryKey(PermissionRoleDO record);
}