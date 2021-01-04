package cn.xjh.softwarereliability.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.jfree.data.xy.XYSeries;

public class YGraph {
    public XYSeries createYGraph() throws IOException {
    	System.out.println("Y图");
        String url = "Source/SYS.txt";
        DataReader dr = new DataReader();
        int[] data = dr.deal(url);
        int dataLen = data.length;
        int trainDataLen = (int) (dataLen * 0.7);//70%训练
        int[] trainData = new int[trainDataLen];
        int[] testData = new int[dataLen - trainDataLen];
        for (int i = 0; i < trainDataLen; i++) {
            trainData[i] = data[i];
            if (i + trainDataLen < dataLen) {
                testData[i] = data[i + trainDataLen];
            }
        }
        System.out.println("traindata："+trainDataLen);
        //训练得到ooo N;
        JM JM_Model = new JM(trainData,0.1,0.1);
        JM_Model.first();
        double N = JM_Model.getResN();
        double ooo = JM_Model.getResΦ();


        int testDataLen = testData.length;
        System.out.println("testdata："+testDataLen);
//        System.out.println(testDataLen);
        TreeSet<Double> xData = new TreeSet<Double>();

        for (int i = 0; i < testDataLen; i++) {
        	
            xData.add(1 - Math.exp(-ooo * (N - trainDataLen + 1) * testData[i]));
        }
        System.out.println("xData.size:"+xData.size());
        System.out.println("testdata.size:"+testDataLen);
        
        List<Double> xDataList = new ArrayList<Double>();

        while (xData.size() != 0) {
            xDataList.add(xData.pollFirst());
        }

        testDataLen = xDataList.size();
        System.out.println("testdata："+testDataLen);
        
        for (int i = 0; i < testDataLen - 1; i++) {
//            System.out.println(xDataList.get(i));
            xData.add(-Math.log(1 - xDataList.get(i)));   //x=-ln(1-u)
        }
//        System.out.println(xDataList.size());
        xDataList.clear();

        while (xData.size() != 0) {
            xDataList.add(xData.pollFirst());
        }
        testDataLen = xDataList.size();
//        System.out.println(xDataList.size());
//        System.out.println(testDataLen);
        double sumBase = 0;
        for (int i = 0; i < testDataLen; i++) {
            sumBase += xDataList.get(i);                //计算xj,s到i的和
        }
        System.out.println("size:"+xData.size());
        for (int i = 0; i < testDataLen; i++) {
            double sumTop = 0;
            for (int j = 0; j <= i; j++) {
                sumTop += xDataList.get(j);             //求xj,j从s到k的和
            }
            xData.add(sumTop / sumBase);                //yk(横坐标）
        }

        TreeSet<Double> yData = new TreeSet<Double>();
        for (int i = 0; i < xData.size(); i++) {
            yData.add(i / (double) (xData.size() + 1));   //y坐标增长步长为1/(i-s+1)
        }

        XYSeries uSeries = new XYSeries("Y-graph");
        double x, y, y2;
        while (xData.size() != 1) {   //画图
            x = xData.pollFirst();
            y = yData.pollFirst();
            uSeries.add(x, y);
            
            y2 = yData.pollFirst();
            uSeries.add(x, y2);
            yData.add(y2);
            
        }
        x = xData.pollFirst();
        y = yData.pollFirst();
        uSeries.add(x, y);
        return uSeries;
    }

}
