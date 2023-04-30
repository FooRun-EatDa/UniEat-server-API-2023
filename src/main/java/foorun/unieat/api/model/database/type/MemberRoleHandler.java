package foorun.unieat.api.model.database.type;

import foorun.unieat.common.rules.MemberRole;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * MemberRole(회원 등급 상태) 열거형과 데이터베이스 문자열 형태와의 매핑을 처리함
 */
public class MemberRoleHandler extends BaseTypeHandler<MemberRole> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MemberRole parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public MemberRole getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return MemberRole.valueOf(rs.getString(columnName));
    }

    @Override
    public MemberRole getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return MemberRole.indexOf(rs.getInt(columnIndex));
    }

    @Override
    public MemberRole getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return MemberRole.indexOf(cs.getInt(columnIndex));
    }
}