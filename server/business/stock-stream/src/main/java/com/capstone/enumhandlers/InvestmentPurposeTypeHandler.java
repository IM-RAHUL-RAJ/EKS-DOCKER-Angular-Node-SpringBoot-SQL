package com.capstone.enumhandlers;

import com.capstone.models.InvestmentPurpose;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvestmentPurposeTypeHandler extends BaseTypeHandler<InvestmentPurpose> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InvestmentPurpose parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getName()); // Use the name for storage
    }

    @Override
    public InvestmentPurpose getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        System.out.println("Retrieved value from DB: " + value); 
        return value != null ? InvestmentPurpose.of(value.trim()) : null;
    }

    @Override
    public InvestmentPurpose getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        System.out.println("Retrieved value from DB: " + value); 
        
        return value != null ? InvestmentPurpose.of(value.trim()) : null;
    }

    @Override
    public InvestmentPurpose getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        System.out.println("Retrieved value from DB: " + value); 
        return value != null ? InvestmentPurpose.of(value.trim()) : null;
    }
}

