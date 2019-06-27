package net.renfei.dao.persistences;

import java.math.BigInteger;
import java.util.List;
import net.renfei.dao.entity.IPV6DO;
import net.renfei.dao.entity.IPV6DOExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface IPV6DOMapper {
    long countByExample(IPV6DOExample example);

    int deleteByExample(IPV6DOExample example);

    int insert(IPV6DO record);

    int insertSelective(IPV6DO record);

    List<IPV6DO> selectByExample(IPV6DOExample example);

    int updateByExampleSelective(@Param("record") IPV6DO record, @Param("example") IPV6DOExample example);

    int updateByExample(@Param("record") IPV6DO record, @Param("example") IPV6DOExample example);

    @Select("SELECT * FROM ip_ipv6 WHERE ip_from <= #{ip} AND ip_to >= #{ip}")
    IPV6DO selectByIP(@Param("ip") BigInteger ip);
}