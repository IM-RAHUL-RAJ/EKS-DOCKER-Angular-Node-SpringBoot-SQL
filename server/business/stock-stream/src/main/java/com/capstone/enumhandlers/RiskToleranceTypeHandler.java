package com.capstone.enumhandlers;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.capstone.models.RiskTolerance;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiskToleranceTypeHandler extends BaseTypeHandler<RiskTolerance> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RiskTolerance parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public RiskTolerance getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value != null ? RiskTolerance.valueOf(value) : null;
    }

    @Override
    public RiskTolerance getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value != null ? RiskTolerance.valueOf(value) : null;
    }

    @Override
    public RiskTolerance getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value != null ? RiskTolerance.valueOf(value) : null;
    }
}
