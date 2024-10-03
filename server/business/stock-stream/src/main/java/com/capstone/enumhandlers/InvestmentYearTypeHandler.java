package com.capstone.enumhandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.capstone.models.InvestmentYear;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvestmentYearTypeHandler extends BaseTypeHandler<InvestmentYear> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InvestmentYear parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public InvestmentYear getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value != null ? InvestmentYear.valueOf(value) : null;
    }

    @Override
    public InvestmentYear getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value != null ? InvestmentYear.valueOf(value) : null;
    }

    @Override
    public InvestmentYear getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value != null ? InvestmentYear.valueOf(value) : null;
    }
}

