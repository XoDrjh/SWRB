
package cn.xjh.softwarereliability.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class DataReader {
	public  int[] deal(String url) throws IOException {
	        ArrayList<String> arrayList = new ArrayList<String>();

			String Filename=url;
			BufferedReader reader=new BufferedReader(new FileReader(Filename));
			
	        String str;
	        while ((str = reader.readLine()) != null) {
	            arrayList.add(str);
	        }
	        reader.close();
	        /*
	         * 根据数据的特点，进行字符串分割
	         */
	        int length = arrayList.size();
	        int[] array = new int[length + 1];
	        array[0] = 0;   
	        for (int i = 1; i <= length; i++) {
	        	
	            String s = arrayList.get(i - 1).split("\\s+")[1];
	            array[i] = Integer.parseInt(s);
	        }
	        return array;
		
	}

}
