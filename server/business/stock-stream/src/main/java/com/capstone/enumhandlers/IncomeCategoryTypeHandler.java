package com.capstone.enumhandlers;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.capstone.models.IncomeCategory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeCategoryTypeHandler extends BaseTypeHandler<IncomeCategory> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IncomeCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public IncomeCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value != null ? IncomeCategory.valueOf(value) : null;
    }

    @Override
    public IncomeCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value != null ? IncomeCategory.valueOf(value) : null;
    }

    @Override
    public IncomeCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value != null ? IncomeCategory.valueOf(value) : null;
    }
}
