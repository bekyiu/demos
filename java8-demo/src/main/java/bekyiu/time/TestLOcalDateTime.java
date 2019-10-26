package bekyiu.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 *  java8提供了一组新的时间和日期的api, 位于java.time包下
 */
public class TestLOcalDateTime
{
    /**
     *  LocalDate, 用于存储日期, 例如 x年x月x日, day-of-year, day-of-week and week-of-year
     *  LocalTime, 表示时间, 例如 x小时x分x秒
     *  LocalDateTime, 以上两者的结合
     *  上面三个类都是不可变的(类似String)和线程安全的
     */
    @Test
    public void test1()
    {
        LocalDateTime ldt1 = LocalDateTime.now();
        System.out.println(ldt1);
        // date
        System.out.println(ldt1.getDayOfMonth());
        // time
        System.out.println(ldt1.getHour());

        LocalDateTime ldt2 = LocalDateTime.of(2020, 6, 30, 12, 30, 59);
        System.out.println(ldt2);
        System.out.println(ldt2.plusDays(1));
    }

    /**
     *  Instant: 时间戳 1970年1月1日00:00:00到每个时间之间的毫秒值
     */
    @Test
    public void test2()
    {
        // 有时差
        Instant now = Instant.now();
        System.out.println(now);
        // 偏移8个小时
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);
        // 返回毫秒值
        System.out.println(now.toEpochMilli());
        // 距离 1970 1 1 00 00 00 偏移一秒
        Instant instant = Instant.ofEpochSecond(1);
        System.out.println(instant.toEpochMilli());
    }

    /**
     *  Duration 计算两个time之间的间隔
     *  Period 计算两个data之间的间隔
     */
    @Test
    public void test3()
    {
        LocalTime lt1 = LocalTime.now();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        LocalTime lt2 = LocalTime.now();
        Duration between = Duration.between(lt1, lt2);
        System.out.println(between.getSeconds());
        System.out.println(between.toMillis());

        System.out.println("--------------------");

        LocalDate ld1 = LocalDate.now();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        LocalDate ld2 = LocalDate.of(2020, 10, 12);
        Period between1 = Period.between(ld1, ld2);
        System.out.println(between1.getYears());
        System.out.println(between1.getMonths());
        System.out.println(between1.getDays());
    }

    /**
     *  TemporalAdjuster 时间矫正器
     */
    @Test
    public void test5()
    {
        LocalDateTime ldt1 = LocalDateTime.now();
        System.out.println(ldt1);
        // 修改ldt1表示的date, 改为ldt1所表示的年, 月, 以及这个月的第十天
        LocalDateTime ldt2 = ldt1.withDayOfMonth(10);
        System.out.println(ldt2);
        // 改为这年的第一天
        LocalDateTime ldt3 = ldt1.withDayOfYear(1);
        System.out.println(ldt3);
        System.out.println("--------------------");
        LocalDateTime ldt5 = ldt1.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(ldt5);
        // ldt1的下一个周五
        LocalDateTime ldt6 = ldt1.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println(ldt6);
        // 自定义 下一个工作日是多少号
        LocalDateTime ldt7 = ldt1.with(temporal ->
        {
            LocalDateTime ldt = (LocalDateTime) temporal;
            DayOfWeek dayOfWeek = ldt.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY))
            {
                return ldt.plusDays(3);
            }
            else if (dayOfWeek.equals(DayOfWeek.SATURDAY))
            {
                return ldt.plusDays(2);
            }
            return ldt.plusDays(1);
        });
        System.out.println(ldt7);
    }

    /**
     *  DateTimeFormatter 格式化时间/日期
     */
    @Test
    public void test6()
    {
        // 日期 -> 格式化字符串
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        LocalDateTime ldt = LocalDateTime.now();
        String format = ldt.format(dtf);
        System.out.println(format);
        System.out.println("-----------------");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format1 = dtf2.format(ldt);
        System.out.println(format1);
        // 格式化字符串 -> 日期
        LocalDateTime newDate = LocalDateTime.parse(format1, dtf2);
        System.out.println(newDate);
    }
}
