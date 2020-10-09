package com.ykr.utils;

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

    //生成题目和答案的映射关系
    public static HashMap<String, String> generateMap(int examNumber, int answerRange) {
        if (examNumber < 1) {
            throw new RuntimeException("生成题目的个数必须大于0");
        }
        if (answerRange < 1) {
            throw new RuntimeException("运算结果范围必须大于等于1");
        }
        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 1; hashMap.size() < examNumber; ) {
            //因为在运算的过程中会出现n÷0的情况，这时候就会抛异常
            Expression expression = new Expression(3, answerRange);
            if ((hashMap.get(expression.toString()) != null || !"".equals(expression.toString()))
                                                             && !expression.isDivideForZero()) {
                hashMap.put(expression.toString(), expression.getRoot().result.toString());
                i++;
            }
        }

        return hashMap;
    }
}
