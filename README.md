
## 功能
 使用 -n 参数控制生成题目的个数
 使用 -r 参数控制题目中数值的范围, 。该参数可以设置为1或其他自然数。
 生成的题目中计算过程不能产生负数
 生成的题目中如果存在形如e1 ÷ e2的子表达式，那么其结果应是真分数。
 程序一次运行生成的题目不能重复,生成的题目存入执行程序的当前目录下的Exercises.txt文件
 每道题目中出现的运算符个数不超过3个
 在生成题目的同时，计算出所有题目的答案，并存入执行程序的当前目录下的Answers.txt文件
 程序应能支持一万道题目的生成。
 程序支持对给定的题目文件和答案文件，判定答案中的对错并进行数量统计

## 二、设计实现过程

### 2.1 数据结构

>  使用**二叉树**存放四则运算式,比如1+2×3÷(4-5)+6这个表达式就可以使用以下二叉树表示，中序遍历二叉树时就可以还原四则运算。

![](https://gitee.com/yuki-r/blog-image/raw/master/img/20201010191558.png)

作业要求四则运算式中不能出现负数，（4-5）在二叉树中调换位置即可。

![](https://gitee.com/yuki-r/blog-image/raw/master/img/20201010191615.png)

### 2.2 实现步骤

![实现步骤](https://gitee.com/yuki-r/blog-image/raw/master/img/20201010200413.png)

![流程图](https://gitee.com/yuki-r/blog-image/raw/master/img/20201010194428.png)

> 在主函数`main()`中输入`-n`，`-r`两个参数， 传入并执行`generateMap()`函数，在`generateMap()` 中用for循环`new` `ArithmeticTree`对象，在`ArithmeticTree`的构造器中，计算了四则运算结果，返回一个`OperatorNode`结点，在`generateMap()`函数调用`ArithmeticTree`对象中的值，包括计算出的结果，调用`hashMap.put()`保存在`HashMap`对象中，再传入`writFile()`写出文件。

## 三、代码说明

| 类                  | 功能                         |
| ------------------- | ---------------------------- |
| `Main`              | 主函数，获取输入值，计算结果 |
| `ArithmeticTree`    | 构建四则运算的二叉树         |
| `FractionOperation` | 分数的四则运算操作           |
| `DataNode`          | 存放数据的结点               |
| `OperatorNode`      | 存放操作符的结点             |
| `FileUtils`         | IO流操作，文件输入输出       |
| `GenerateUtils`     | 随机生成四则运算             |

```
   /**
     * 获取随机范围内的随机整数
     * @param range 范围
     * @return 随机数
     */
    public static int getRandomInRange(int range) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(range);
    }
```

```
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
```

## 四、测试运行

![输入](https://gitee.com/yuki-r/blog-image/raw/master/img/20201010201759.png)

![生成文件](https://gitee.com/yuki-r/blog-image/raw/master/img/20201010201943.png)

![10000道题目](https://gitee.com/yuki-r/blog-image/raw/master/img/20201010201844.png)

![答案](https://gitee.com/yuki-r/blog-image/raw/master/img/20201010201910.png)

