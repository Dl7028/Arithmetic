package com.ykr.utils;

import com.ykr.main.DataNode;
import com.ykr.main.OperatorNode;

/** 生成四则运算表达式
 * 构建二叉树
 * @author Yuki-r
 * @date 2020/10/9 20:41
 */
public class Expression {
    //运算符号
    private static final String ADD = "＋";

    private static final String SUBTRACT = "-";

    private static final String MULTIPLY = "×";

    private static final String DIVIDE = "÷";

    private static final String LEFT_BRACKETS = "(";

    private static final String RIGHT_BRACKETS = ")";
    //运算符数组
    private static final String[] OPERATORS = {ADD, SUBTRACT, MULTIPLY, DIVIDE};
    //根节点
    private DataNode root;
    //生成答案的范围
    public static int range;

    //出现n除以0的情况
    private boolean isDivideForZero = false;
    public DataNode getRoot() {
        return root;
    }

    public void setRoot(DataNode root) {
        this.root = root;
    }
    public boolean isDivideForZero() {
        return isDivideForZero;
    }

    public void setDivideForZero(boolean divideForZero) {
        isDivideForZero = divideForZero;
    }


    /**
     * 构造器 生成表达式
     * @param operatorNumber 运算符数量
     * @param answerRange 生成数的范围
     */
    public Expression(int operatorNumber, int answerRange) {
        if (operatorNumber < 1) {
            throw new RuntimeException("运算符个数必须大于0");
        }
        if (answerRange < 1) {
            throw new RuntimeException("运算结果范围必须大于等于1");
        }
        range = answerRange;

        if (operatorNumber == 1) {
            root = generateNode(operatorNumber);
        } else {
//            root = generateNode(GenerateUtils.getRandomInRange(operatorNumber) + 1);
        }
    }
    public DataNode generateNode(int number) {
        //如果是0就构造叶子节点
        if (number == 0) {
            return new DataNode(Fraction.generateFraction(), null, null, 1);
        }
        //其他都是构造符号节点
        OperatorNode parent = new OperatorNode(null, null, OPERATORS[GenerateUtils.getRandomInRange(4)]);
        int left = GenerateUtils.getRandomInRange(number);
        //递归下去构造左孩子和右孩子
        parent.left = generateNode(left);
        //总数要减去当前已经构建出来的这一个节点
        parent.right = generateNode(number - 1 - left);

        //然后计算结果
        Fraction result = calculate(parent.operator, parent.left.result, parent.right.result);
        //如果是负数,就是出现小的减去大的情况，这时候交换左右孩子
        if (result.isNegative()) {
            DataNode tmp = parent.left;
            parent.left = parent.right;
            parent.right = tmp;
        }
        parent.result = result;
        //计算树高
        parent.high = Math.max(parent.left.high, parent.right.high) + 1;
        return parent;
    }


    /**
     * 进行两个元素的计算
     * @param operator 运算符
     * @param leftFraction  左节点分数
     * @param rightFraction 右节点分数
     * @return 运算结果
     */
    private Fraction calculate(String operator, Fraction leftFraction, Fraction rightFraction) {
        switch (operator) {
            case ADD:
                return leftFraction.add(rightFraction);
            case SUBTRACT:
                return leftFraction.subtract(rightFraction);
            case MULTIPLY:
                return leftFraction.multiply(rightFraction);
            //可能会出现除以0的情况，即rightFraction可能为0
            case DIVIDE:
                if (rightFraction.getA() == 0) {
                    this.isDivideForZero = true;
                    rightFraction.setA(1);
                }
                return leftFraction.divide(rightFraction);
            default:
                throw new RuntimeException("该操作符不存在");
        }
    }
}
