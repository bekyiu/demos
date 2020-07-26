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
    // 设置占位符参数
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Sex sex, JdbcType jdbcType) throws SQLException
    {
        if(sex == null)
        {
            return;
        }
        ps.setObject(i, sex.getCode());

    }

    // 从数据库拿到值，创建出枚举对象再返回
    @Override
    public Sex getNullableResult(ResultSet rs, String columnName) throws SQLException
    {
        if(rs == null)
        {
            return null;
        }
        Integer code = rs.getInt(columnName);
        return code.equals(Sex.WOMAN.getCode()) ? Sex.WOMAN : Sex.MAN;
    }

    @Override
    public Sex getNullableResult(ResultSet rs, int columnIndex) throws SQLException
    {
        return getNullableResult(rs, String.valueOf(columnIndex));
    }

    @Override
    public Sex getNullableResult(CallableStatement cs, int i) throws SQLException
    {
        if(cs == null)
        {
            return null;
        }
        Integer code = cs.getInt(i);
        return code.equals(Sex.WOMAN.getCode()) ? Sex.WOMAN : Sex.MAN;
    }
}
