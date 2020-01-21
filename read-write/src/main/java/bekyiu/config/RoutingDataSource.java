package bekyiu.config;

import bekyiu.utils.RoutingUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource
{
    @Override
    protected Object determineCurrentLookupKey()
    {
        System.out.println("选择操作的连接池: " + RoutingUtil.get());
        return RoutingUtil.get();
    }
}
