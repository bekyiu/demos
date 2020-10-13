package bekyiu.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangyc
 * @Date: 2020/10/10 21:48
 */
public class MessageUtils
{
    public static String buildJson(String userId, String username,
                                   Boolean online, Boolean isSystemInfo, Object data)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("username", username);
        map.put("isSystemInfo", isSystemInfo);
        map.put("online", online);
        map.put("data", data);
        return JSON.toJSONString(map);
    }
}
