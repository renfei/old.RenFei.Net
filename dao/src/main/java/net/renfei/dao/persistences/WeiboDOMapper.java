package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.WeiboDO;
import net.renfei.dao.entity.WeiboDOExample;
import org.apache.ibatis.annotations.Param;

public interface WeiboDOMapper {
    long countByExample(WeiboDOExample example);

    int deleteByExample(WeiboDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WeiboDO record);

    int insertSelective(WeiboDO record);

    List<WeiboDO> selectByExample(WeiboDOExample example);

    WeiboDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WeiboDO record, @Param("example") WeiboDOExample example);

    int updateByExample(@Param("record") WeiboDO record, @Param("example") WeiboDOExample example);

    int updateByPrimaryKeySelective(WeiboDO record);

    int updateByPrimaryKey(WeiboDO record);
}