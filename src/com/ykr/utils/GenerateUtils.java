package com.ykr.utils;

import com.ykr.main.ArithmeticTree;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生成四则运算式
 * @author Yuki-r
 * @date 2020/10/9 21:16
 */
public class GenerateUtils {

    /**
     * 获取随机范围内的随机整数
     * @param range 范围
     * @return 随机数
     */
    public static int getRandomInRange(int range) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(range);
    }

 
}
