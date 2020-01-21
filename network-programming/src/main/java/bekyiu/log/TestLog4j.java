package bekyiu.log;


import org.apache.log4j.Logger;

public class TestLog4j
{
    private static Logger logger = Logger.getLogger(TestLog4j.class);

    public static void main(String[] args)
    {
        logger.info("info");
        logger.debug("debug");
        logger.warn("warn");
        logger.error("error");
        logger.fatal("fatal");
        logger.error("error: ", new IllegalArgumentException("错错错"));
    }
}
