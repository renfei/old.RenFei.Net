package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.ShortUrl;
import net.renfei.dao.entity.ShortUrlExample;
import org.apache.ibatis.annotations.Param;

public interface ShortUrlMapper {
    long countByExample(ShortUrlExample example);

    int deleteByExample(ShortUrlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ShortUrl record);

    int insertSelective(ShortUrl record);

    List<ShortUrl> selectByExample(ShortUrlExample example);

    ShortUrl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ShortUrl record, @Param("example") ShortUrlExample example);

    int updateByExample(@Param("record") ShortUrl record, @Param("example") ShortUrlExample example);

    int updateByPrimaryKeySelective(ShortUrl record);

    int updateByPrimaryKey(ShortUrl record);
}