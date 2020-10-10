package com.ykr.main;


import com.ykr.utils.FileUtils;
import com.ykr.utils.GenerateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        //数字范围大小
        int range=0;
        //题目数量
        int number=0;
        //接收参数，输入
        // -n number -r range
        while (true) {
            System.out.println("输入 “-n 生成数量 -r 数字范围” ");
            Scanner sc = new Scanner(System.in);
            String string = sc.nextLine();
            args = string.split("\\s+"); //去除空格
            //判断参数是否正确
            if (args.length < 2) {
                System.out.println("请重新输入正确的参数...");
            }
            else {
                break;
            }
        }
        //获取参数
        for(int i=0;i<args.length;i++){
            if("-n".equals(args[i])){
                number= Integer.parseInt(args[i+1]);
                i++;
            } else if ("-r".equals(args[i])) {
                range= Integer.parseInt(args[i+1]);
                i++;
            }
            else {
                break;
            }
        }
        //判断是否生成题目,如果不是生成题目，则是对照答案
        //Myapp.exe -e Exercisefile.txt -a Answerfile.txt
        if(range==0&&number==0){       //对照答案
            String answerFileName;
            String execiseFileName;
            if ("-e".equals(args[0])){
                execiseFileName=args[1];
                answerFileName=args[3];
            }else {
                execiseFileName=args[3];
                answerFileName=args[1];
            }
            File answerFile=new File(answerFileName);
            File exerciseFile=new File(execiseFileName);
            FileUtils.compare(answerFile,exerciseFile);
        } else {     //生成四则运算
            HashMap<String, String> map= GenerateUtils.generateMap(10,10);
            File file=new File("Exercisefile2.txt");
            File answerFile=new File("Answerfile2.txt");
            try {
                FileWriter fileWriter=new FileWriter(file,true);
                PrintWriter printWriter=new PrintWriter(fileWriter);
                FileUtils.writeTitle(printWriter,map);
                printWriter.flush();
                fileWriter.flush();
                printWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileWriter fileWriter=new FileWriter(answerFile,true);
                PrintWriter printWriter=new PrintWriter(fileWriter);
                FileUtils.writeAnswer(printWriter,map);
                printWriter.flush();
                fileWriter.flush();
                printWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
