package bekyiu.enums;

import lombok.Getter;

/**
 * @Author: wangyc
 * @Date: 2020/7/25 21:48
 */
@Getter
public enum Sex
{
    MAN(0, "男"), WOMAN(1, "女");

    private int code;
    private String desc;

    Sex(int code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }
}
