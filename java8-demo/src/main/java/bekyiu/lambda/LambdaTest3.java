package bekyiu.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *  四大内置核心函数式接口:
 *  Consumer<T>
 *      void accept(T t);
 *
 *  Supplier<T>
 *      T get();
 *
 *  Function<T, R>
 *      R apply(T t);
 *
 *  Predicate<T>
 *      boolean test(T t);
 */
public class LambdaTest3
{
    public static void main(String[] args)
    {
        String[] strs = {"Louise", "Chitoge", "Yukino", "xx", "bekyiu"};
        // 返回字符串长度大于5的所有字符串
        List<String> list = filter(strs, s -> s.length() > 5);
        System.out.println(list);
    }

    public static List<String> filter(String[] strs, Predicate<String> p)
    {
        List<String> list = new ArrayList<>();
        for (String str : strs)
        {
            if(p.test(str))
            {
                list.add(str);
            }
        }
        return list;
    }
}
