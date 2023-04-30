package foorun.unieat.api.model.database.type;

import foorun.unieat.common.rules.ManagedStatusType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * MyBatis의 TypeHandler를 구현한 클래스
 * TypeHandler는 JDBC 타입과 자바 타입간의 매핑을 처리하는 인터페이스임
 * ManagedStatusType(회원 계정 상태) 열거형과 데이터베이스 문자열 형태와의 매핑을 처리함
 */
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
