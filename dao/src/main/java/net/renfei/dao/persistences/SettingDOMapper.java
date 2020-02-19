package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.SettingDO;
import net.renfei.dao.entity.SettingDOExample;
import net.renfei.dao.entity.SettingDOWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SettingDOMapper {
    long countByExample(SettingDOExample example);

    int deleteByExample(SettingDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SettingDOWithBLOBs record);

    int insertSelective(SettingDOWithBLOBs record);

    List<SettingDOWithBLOBs> selectByExampleWithBLOBs(SettingDOExample example);

    List<SettingDO> selectByExample(SettingDOExample example);

    SettingDOWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SettingDOWithBLOBs record, @Param("example") SettingDOExample example);

    int updateByExampleWithBLOBs(@Param("record") SettingDOWithBLOBs record, @Param("example") SettingDOExample example);

    int updateByExample(@Param("record") SettingDO record, @Param("example") SettingDOExample example);

    int updateByPrimaryKeySelective(SettingDOWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SettingDOWithBLOBs record);

    int updateByPrimaryKey(SettingDO record);

    @Select("select * from t_setting where `keys` = #{keys,jdbcType=LONGVARCHAR} ORDER BY orders ASC")
    List<SettingDOWithBLOBs> selectByKeys(@Param("keys") String keys);

    @Select("select * from t_setting where `keys` = #{keys,jdbcType=LONGVARCHAR} AND extend = #{extend,jdbcType=LONGVARCHAR} ORDER BY orders ASC")
    List<SettingDOWithBLOBs> selectByKeysAndExtend(@Param("keys") String keys,@Param("extend") String extend);

    @Select("select * from t_setting where `keys` = #{keys,jdbcType=LONGVARCHAR} AND extend IS NULL ORDER BY orders ASC")
    List<SettingDOWithBLOBs> selectByKeysAndExtendNull(@Param("keys") String keys);
}