package cn.xjh.softwarereliability;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.xjh.softwarereliability.dao")
@SpringBootApplication
public class SoftwarereliabilityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwarereliabilityApplication.class, args);
    }

}
