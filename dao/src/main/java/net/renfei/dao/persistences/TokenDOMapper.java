package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.TokenDO;
import net.renfei.dao.entity.TokenDOExample;
import org.apache.ibatis.annotations.Param;

public interface TokenDOMapper {
    long countByExample(TokenDOExample example);

    int deleteByExample(TokenDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TokenDO record);

    int insertSelective(TokenDO record);

    List<TokenDO> selectByExample(TokenDOExample example);

    TokenDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TokenDO record, @Param("example") TokenDOExample example);

    int updateByExample(@Param("record") TokenDO record, @Param("example") TokenDOExample example);

    int updateByPrimaryKeySelective(TokenDO record);

    int updateByPrimaryKey(TokenDO record);
}