package bekyiu.util;

import java.util.Map;

/**
 * @Author: wangyc
 * @Date: 2020/12/27 20:56
 */
public class RedisKey
{
    public static String userKey(Map<String, Object> map) {
        return "user:" + map.get("id") + ":" + map.get("start");
    }
}
