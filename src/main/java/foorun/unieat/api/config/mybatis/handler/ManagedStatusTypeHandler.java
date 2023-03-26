package foorun.unieat.api.config.mybatis.handler;

import foorun.unieat.common.rules.ManagedStatusType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagedStatusTypeHandler extends BaseTypeHandler<ManagedStatusType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ManagedStatusType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public ManagedStatusType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ManagedStatusType.valueOf(rs.getString(columnName));
    }

    @Override
    public ManagedStatusType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ManagedStatusType.indexOf(rs.getInt(columnIndex));
    }

    @Override
    public ManagedStatusType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ManagedStatusType.indexOf(cs.getInt(columnIndex));
    }
}
