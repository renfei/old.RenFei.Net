package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.SecretKeyDO;
import net.renfei.dao.entity.SecretKeyDOExample;
import net.renfei.dao.entity.SecretKeyDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface SecretKeyDOMapper {
    long countByExample(SecretKeyDOExample example);

    int deleteByExample(SecretKeyDOExample example);

    int deleteByPrimaryKey(byte[] uid);

    int insert(SecretKeyDOWithBLOBs record);

    int insertSelective(SecretKeyDOWithBLOBs record);

    List<SecretKeyDOWithBLOBs> selectByExampleWithBLOBs(SecretKeyDOExample example);

    List<SecretKeyDO> selectByExample(SecretKeyDOExample example);

    SecretKeyDOWithBLOBs selectByPrimaryKey(@Param("uid") byte[] uid);

    SecretKeyDOWithBLOBs selectByUid(@Param("uid") String uid);

    int updateByExampleSelective(@Param("record") SecretKeyDOWithBLOBs record, @Param("example") SecretKeyDOExample example);

    int updateByExampleWithBLOBs(@Param("record") SecretKeyDOWithBLOBs record, @Param("example") SecretKeyDOExample example);

    int updateByExample(@Param("record") SecretKeyDO record, @Param("example") SecretKeyDOExample example);

    int updateByPrimaryKeySelective(SecretKeyDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SecretKeyDOWithBLOBs record);

    int updateByPrimaryKey(SecretKeyDO record);
}