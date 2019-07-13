package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.RoleAccountDO;
import net.renfei.dao.entity.RoleAccountDOExample;
import org.apache.ibatis.annotations.Param;

public interface RoleAccountDOMapper {
    long countByExample(RoleAccountDOExample example);

    int deleteByExample(RoleAccountDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleAccountDO record);

    int insertSelective(RoleAccountDO record);

    List<RoleAccountDO> selectByExample(RoleAccountDOExample example);

    RoleAccountDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleAccountDO record, @Param("example") RoleAccountDOExample example);

    int updateByExample(@Param("record") RoleAccountDO record, @Param("example") RoleAccountDOExample example);

    int updateByPrimaryKeySelective(RoleAccountDO record);

    int updateByPrimaryKey(RoleAccountDO record);
}