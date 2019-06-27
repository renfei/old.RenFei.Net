package net.renfei.core.entity;

import lombok.Data;

import java.math.BigInteger;

@Data
public class IPDTO {
    private String ip;
    private BigInteger ipBigInt;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String cityName;
    private Double latitude;
    private Double longitude;
    private String zipCode;
    private String timeZone;
}
