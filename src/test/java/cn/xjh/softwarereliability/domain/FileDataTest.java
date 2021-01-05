package cn.xjh.softwarereliability.domain;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileDataTest {

    @Test
    void read() throws IOException {
        FileData fileData = new FileData();
        String filename = "D:\\idea_works\\softwarereliability\\src\\main\\resources\\sourceData.txt";
        double[] data = fileData.read(filename);
        for(int i=0; i < data.length; i ++) {
            System.out.println(data[i]);
        }
    }
}