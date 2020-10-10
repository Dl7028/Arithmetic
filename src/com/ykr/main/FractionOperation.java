package com.ykr.main;

import com.ykr.utils.GenerateUtils;

/**
 * 分数的四则运算
 * @author Yuki-r
 * @date 2020/10/9 20:23
 */
public class FractionOperation {
    //分子
    private int a;
    //分母，不能为0，默认为1
    private int b = 1;

    public int getA() {
        return a;
    }
    public void setA(int a) {
        this.a = a;
    }
    public int getB() {
        return b;
    }
    public void setB(int b) {
        this.b = b;
    }

    /**
     * 设置分子和分母
     * @param a 分子
     * @param b 分母
     */
    public FractionOperation(int a, int b) {
        setAB(a, b);
    }

    /**
     * 通过表达式得到分子分母
     * @param expression 表达式
     */
    public FractionOperation(String expression) {
        expression.trim();
        int aIndex = expression.indexOf("/");
        int bIndex = expression.indexOf("'");

        if (aIndex == -1) {        //不是分式的时候
            a = Integer.parseInt(expression);
        }
        else {        //是分式的时候
            b = Integer.parseInt(expression.substring(aIndex + 1));//分母
            if (bIndex == -1) {            //真分数
                a = Integer.parseInt(expression.substring(0, aIndex));
            }
            else {            //带分数
                int a1 = Integer.parseInt(expression.substring(0, bIndex));
                int a0 = Integer.parseInt(expression.substring(bIndex + 1, aIndex));
                a = a1 * b + a0;
            }
        }
        setAB(a, b);
    }

    /**
     * 生成一个计算数
     * @return 随机数
     */
    public static FractionOperation generateFraction() {
        //a.b 都是大于等于0的
        int a = GenerateUtils.getRandomInRange(ArithmeticTree.range);
        int b = GenerateUtils.getRandomInRange(ArithmeticTree.range);
        //分母为0
        while (b == 0) {
            b = GenerateUtils.getRandomInRange(ArithmeticTree.range);
        }
        FractionOperation result = new FractionOperation(a, b);
        return result;
    }


    /**
     * 加法
     * @param right 右节点分数
     * @return 结果
     */
    public FractionOperation add(FractionOperation right) {
        // a/b+c/d =（ad+bc）/bd
        return new FractionOperation(
                this.a * right.b + this.b * right.a,
                this.b * right.b
        );
    }

    /**
     * 减法
     * @param right 右节点分数
     * @return 结果
     */
    public FractionOperation subtract(FractionOperation right) {
        // a/b-c/d =（ad-bc）/bd
        return new FractionOperation(
                this.a * right.b - this.b * right.a,
                this.b * right.b
        );
    }

    /**
     * 乘法
     * @param right 右节点分数
     * @return 结果
     */
    public FractionOperation multiply(FractionOperation right) {
        // a/b * c/d = ac / bd
        return new FractionOperation(
                this.a * right.a,
                this.b * right.b
        );
    }

    /**
     * 除法
     * @param right 右节点分数
     * @return 结果
     */
    public FractionOperation divide(FractionOperation right) {
        // a/b  /  c/d = ad / bc
        return new FractionOperation(
                this.a * right.b,
                this.b * right.a
        );
    }




    /**
     * 将分子分母调整之后，存储到成员变量中
     * @param a 分子
     * @param b 分母
     */
    public void setAB(int a, int b) {
        if (b == 0)
            throw new RuntimeException("分母不能为0");
        //结果默认是正数
        int isNagitiveAB = 1;
        //调整符号，b只能为正数
        if (a * b < 0) {
            isNagitiveAB = -1;
        }
        a = Math.abs(a);
        b = Math.abs(b);
        //最大公因数
        int g = gcd(a, b);
        //化简分式
        this.a = a * isNagitiveAB / g;
        this.b = b / g;

    }

    /**
     * 求最大公因数 ，辗转相除法
     * @param a 分子
     * @param b 分母
     * @return 最大公因数
     */
    private int gcd(int a, int b) {
        int big = a;
        if (big == 0)
            return 1;
        int small = b;
        //让a成为最大的
        if (a < b) {
            big = b;
            small = a;
        }
        int mod = big % small;
        return mod == 0 ? small : gcd(small, mod);
    }

    @Override
    public String toString() {
        if (b == 1) {
            return String.valueOf(a);
        } else {
            int i = a / b;
            if (i != 0) {
                return String.format("%d'%d/%d", i, Math.abs(a) - b * Math.abs(i), b); //假分式
            } else {
                return String.format("%d/%d", a, b); //真分式
            }
        }
    }


    /**
     * 是否是负数
     *
     * @return a < 0
     */
    public boolean isNegative() {
        return a < 0;
    }
    //判断分式是否相等
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FractionOperation fraction = (FractionOperation) o;
        if (a != fraction.a) return false;
        return b == fraction.b;
    }
    //根据分子和分母
    @Override
    public int hashCode() {
        int result = 31 * a + b;
        return result;
    }

}
