package com.ykr.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 文件操作类
 * @author Yuki-r
 * @date 2020/10/9 19:08
 */
public class FileUtils {


    /**
     * 将题目写入文件
     * @param printWriter IO
     * @param map 题目
     */
    public  static void writeTitle(PrintWriter printWriter, Map<String,String> map){
        Set<String> titles=map.keySet();
        int i=1;
        for(String title:titles){
            printWriter.println(i+":  "+title);
            i++;
        }
    }

    /**
     * 将答案写入文件
     * @param printWriter IO
     * @param map 答案
     */
    public static void  writeAnswer(PrintWriter printWriter,Map<String,String> map){
        Set<String> answer=map.keySet();
        int i=1;
        for (String key :answer){
            String value=map.get(key);
            printWriter.println(i+":  "+value);
            i++;
        }
    }

    /**
     *
     * @param answerFile 答案文件
     * @param exerciseFile  练习文件
     * @throws IOException IO异常
     */
    public static void compare(File answerFile, File exerciseFile) throws IOException {
        if (!exerciseFile.exists()) {
            System.out.println("练习答案文件不存在");
            return;
        }
        if (!answerFile.exists()) {
            System.out.println("答案文件不存在");
            return;
        }
        //key是题号，value是答案
        Map<Integer, String> exerciseMap = new HashMap<>();
        Map<Integer, String> answerMap = new HashMap<>();
        //对比的结果
        List<Integer> rightRsult=new LinkedList<>();
        List<Integer>  errorRsult=new LinkedList<>();
        InputStreamReader exerciseIn = new InputStreamReader(new FileInputStream(exerciseFile.getAbsolutePath()), StandardCharsets.UTF_8);
        InputStreamReader answerIn = new InputStreamReader(new FileInputStream(answerFile.getAbsolutePath()), StandardCharsets.UTF_8);
        BufferedReader exerciseReader = new BufferedReader(exerciseIn);
        BufferedReader answerReader = new BufferedReader(answerIn);
        String string = null;
        //存储练习的答案
        while ((string = exerciseReader.readLine()) != null) {
            string = string.replaceAll(" +", "");
            string = string.replaceAll("\uFEFF", "");
            String TEXT=string.split("[:]")[0];
            exerciseMap.put(Integer.valueOf(string.split("[:]")[0]), string.split(":")[1]);
        }
        while ((string = answerReader.readLine()) != null) {
            string = string.replaceAll(" +", "");
            string = string.replaceAll("\uFEFF", "");
            answerMap.put(Integer.valueOf(string.split("[:]")[0]), string.split(":")[1]);
        }
        exerciseReader.close();
        answerReader.close();

        //比较答案
        for (int i = 1; i <= answerMap.size(); i++){
            if(exerciseMap.containsKey(i)){
                if(exerciseMap.get(i).equals(answerMap.get(i))){
                    rightRsult.add(i);
                }else {
                    errorRsult.add(i);
                }
            }
        }
        //将比较结果存储到文件中
        File file=new File("Grade.txt");
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(" ");
        printWriter.print("Correct:正确题数："+rightRsult.size()+"(");
        for (int str: rightRsult) {
            printWriter.print(str+",");
        }
        printWriter.println(")");
        printWriter.print("Wrong:错误题数："+errorRsult.size()+"(");
        for (int str: errorRsult) {
            printWriter.print(str+",");
        }
        printWriter.print(")");
        printWriter.flush();
        fileWriter.flush();
        printWriter.close();
        fileWriter.close();
    }


}
