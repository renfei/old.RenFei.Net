package net.renfei.dao.persistences;

import java.math.BigInteger;
import java.util.List;
import net.renfei.dao.entity.IPV4DO;
import net.renfei.dao.entity.IPV4DOExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface IPV4DOMapper {
    long countByExample(IPV4DOExample example);

    int deleteByExample(IPV4DOExample example);

    int insert(IPV4DO record);

    int insertSelective(IPV4DO record);

    List<IPV4DO> selectByExample(IPV4DOExample example);

    int updateByExampleSelective(@Param("record") IPV4DO record, @Param("example") IPV4DOExample example);

    int updateByExample(@Param("record") IPV4DO record, @Param("example") IPV4DOExample example);

    @Select("SELECT * FROM ip_ipv4 WHERE ip_from <= #{ip} AND ip_to >= #{ip}")
    IPV4DO selectByIP(@Param("ip") BigInteger ip);
}