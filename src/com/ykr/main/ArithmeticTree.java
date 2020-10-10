package com.ykr.main;

import com.ykr.utils.GenerateUtils;

/** 生成四则运算表达式
 * 构建二叉树
 * @author Yuki-r
 * @date 2020/10/9 20:41
 */
public class ArithmeticTree {
    //运算符号
    private static final String ADD = "＋";

    private static final String SUBTRACT = "-";

    private static final String MULTIPLY = "×";

    private static final String DIVIDE = "÷";

    private static final String LEFT_BRACKETS = "(";

    private static final String RIGHT_BRACKETS = ")";
    //运算符数组
    private static final String[] OPERATORS = {ADD, SUBTRACT, MULTIPLY, DIVIDE};
    //数据节点
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
    public ArithmeticTree(int operatorNumber, int answerRange) {
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
            root = generateNode(GenerateUtils.getRandomInRange(operatorNumber) + 1);
        }
    }

    /**
     * 构建生成四则运算表达式的二叉树
     * @param number 运算符数量
     * @return 二叉树头节点
     */
    public DataNode generateNode(int number) {
        //如果是0就构造叶子节点
        if (number == 0) {
            return new DataNode(FractionOperation.generateFraction(), null, null, 1);
        }
        //其他都是构造符号节点
        OperatorNode rootNode = new OperatorNode(null, null, OPERATORS[GenerateUtils.getRandomInRange(4)]);
        int leftNum = GenerateUtils.getRandomInRange(number);
        //递归下去构造左孩子和右孩子
        rootNode.left = generateNode(leftNum);
        //总数要减去当前已经构建出来的这一个节点
        rootNode.right = generateNode(number - 1 - leftNum);
        //计算结果
        FractionOperation result = calculate(rootNode.operator, rootNode.left.result, rootNode.right.result);
        //如果是负数,交换左右孩子
        if (result.isNegative()) {
            DataNode tmp = rootNode.left;
            rootNode.left = rootNode.right;
            rootNode.right = tmp;
        }
        rootNode.result = result;
        rootNode.high = Math.max(rootNode.left.high, rootNode.right.high) + 1;
        return rootNode;
    }


    /**
     * 进行两个元素的计算
     * @param operator 运算符
     * @param leftFraction  左节点分数
     * @param rightFraction 右节点分数
     * @return 运算结果
     */
    private FractionOperation calculate(String operator, FractionOperation leftFraction, FractionOperation rightFraction) {
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






    /**
     * 中序遍历二叉树
     * @param localRootNode 当前所在的最高节点
     * @return 字符串
     */
    private String print(DataNode localRootNode) {

        if (localRootNode == null) {
            return "";
        }
        String left = print(localRootNode.left);
        String mid = localRootNode.toString();
        //需要加括号的情况,一个节点的操作符为乘除，其子节点的操作符是加减
        if (localRootNode.left instanceof OperatorNode && localRootNode instanceof OperatorNode) {
            if (leftBrackets(((OperatorNode) localRootNode.left).operator, ((OperatorNode) localRootNode).operator)) {
                left = LEFT_BRACKETS + " " + left + " " + RIGHT_BRACKETS;
            }
        }
        String right = print(localRootNode.right);
        if (localRootNode.right instanceof OperatorNode && localRootNode instanceof OperatorNode) {
            if (rightBrackets(((OperatorNode) localRootNode.right).operator, ((OperatorNode) localRootNode).operator)) {
                right = LEFT_BRACKETS + " " + right + " " + RIGHT_BRACKETS;
            }
        }
        return left + mid + right;
    }


    private boolean leftBrackets(String left, String mid) {
        return (isAddOrSubtract(left) && isMultiplyOrDivide(mid));
    }
    //向右遍历时，需要括号
    private boolean rightBrackets(String right, String mid) {
        //有可能出现2*3 /( 4*5 )的情况，所以不用加括号只有当
        return !(isAddOrSubtract(mid) && isMultiplyOrDivide(right));
    }
    private boolean isAddOrSubtract(String operator) {
        return operator.equals(ADD) || operator.equals(SUBTRACT);
    }
    private boolean isMultiplyOrDivide(String operator) {
        return operator.equals(MULTIPLY) || operator.equals(DIVIDE);
    }
    //打印出中缀表达式，包括括号
    @Override
    public String toString() {
        return print(root);
    }

}
