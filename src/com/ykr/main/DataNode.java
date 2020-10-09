package com.ykr.main;

import com.ykr.utils.Fraction;


/**
 * 存放分数数据的节点
 * @author Yuki-r
 * @date 2020/10/9 21:08
 */
public class DataNode {
    //存储当前节点以下的计算结果
    public Fraction result;
    public DataNode left;
    public DataNode right;
    public int high;

    public DataNode(){

    }
    public DataNode(Fraction result, DataNode left, DataNode right, int high) {
        this.result = result;
        this.left = left;
        this.right = right;
        this.high = high;
    }
    //打印出表达式
    @Override
    public String toString() {
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataNode node = (DataNode) o;

        if (result != null ? !result.equals(node.result) : node.result != null)
            return false;
        if (right != null ? !right.equals(node.right) : node.right != null)
            return false;
        return left != null ? left.equals(node.left) : node.left == null;
    }
    //疯狂递归
    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (right != null ? right.hashCode() : 0);
        result1 = 31 * result1 + (left != null ? left.hashCode() : 0);
        return result1;
    }
}
