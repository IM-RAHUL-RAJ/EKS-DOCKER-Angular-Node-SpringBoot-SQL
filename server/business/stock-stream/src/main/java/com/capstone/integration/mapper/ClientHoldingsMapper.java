package com.capstone.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.capstone.models.Holding;

@Mapper
public interface ClientHoldingsMapper {
    List<Holding> getAllHoldings(@Param("clientId") String clientId);
    Holding getHolding(@Param("clientId") String clientId, @Param("instrumentId") String instrumentId);
    int insertHolding(Holding holding);
    int updateHolding(Holding holding);
    int deleteHolding(@Param("clientId") String clientId, @Param("instrumentId") String instrumentId);
}