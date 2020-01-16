package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.WeChatKeywordDO;
import net.renfei.dao.entity.WeChatKeywordDOExample;
import org.apache.ibatis.annotations.Param;

public interface WeChatKeywordDOMapper {
    long countByExample(WeChatKeywordDOExample example);

    int deleteByExample(WeChatKeywordDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WeChatKeywordDO record);

    int insertSelective(WeChatKeywordDO record);

    List<WeChatKeywordDO> selectByExample(WeChatKeywordDOExample example);

    WeChatKeywordDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WeChatKeywordDO record, @Param("example") WeChatKeywordDOExample example);

    int updateByExample(@Param("record") WeChatKeywordDO record, @Param("example") WeChatKeywordDOExample example);

    int updateByPrimaryKeySelective(WeChatKeywordDO record);

    int updateByPrimaryKey(WeChatKeywordDO record);
}