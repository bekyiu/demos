package bekyiu.utils;

public class RoutingUtil
{
    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void setMaster()
    {
        local.set("master");
        System.out.println("设置连接池:master");
    }

    public static void setSlave()
    {
        local.set("slave");
        System.out.println("设置连接池:slave");
    }

    public static String get()
    {
        return local.get();
    }
}
