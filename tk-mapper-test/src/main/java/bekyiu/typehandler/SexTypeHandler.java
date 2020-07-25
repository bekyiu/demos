package bekyiu.typehandler;

import bekyiu.enums.Sex;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: wangyc
 * @Date: 2020/7/25 21:59
 */
public class SexTypeHandler extends BaseTypeHandler<Sex>
{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Sex sex, JdbcType jdbcType) throws SQLException
    {
        if(sex == null)
        {
            return;
        }
        ps.setObject(i, sex.getCode());

    }

    @Override
    public Sex getNullableResult(ResultSet rs, String s) throws SQLException
    {
        if(rs == null)
        {
            return null;
        }
        Integer code = rs.getInt(s);
        return code.equals(Sex.WOMAN.getCode()) ? Sex.WOMAN : Sex.MAN;
    }

    @Override
    public Sex getNullableResult(ResultSet rs, int i) throws SQLException
    {
        return getNullableResult(rs, String.valueOf(i));
    }

    @Override
    public Sex getNullableResult(CallableStatement cs, int i) throws SQLException
    {

        return null;
    }
}
