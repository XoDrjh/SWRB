package cn.xjh.softwarereliability.domain;

import java.io.*;

public class FileData {

    public double[] read(String filename) throws IOException {
        BufferedReader readTxt=new BufferedReader(new FileReader(new File(filename)));

        String textLine;

        String str="";

        while(( textLine=readTxt.readLine())!=null){

            str+=" "+ textLine;

        }

        String[] numbers=str.split("	");
        double []number=new double[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            number[i]=Double.parseDouble(numbers[i]);
//            System.out.println(number[i]);
        }
        readTxt.close();
        double []data=new double[numbers.length/2];
        for(int i = 0, j = 1; j < numbers.length; i ++, j += 2) {
            data[i] = number[j];
        }
        return data;
    }
}
