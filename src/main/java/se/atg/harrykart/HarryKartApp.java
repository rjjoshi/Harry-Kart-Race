package se.atg.harrykart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"se.atg.harrykart.requestdto"})
public class HarryKartApp {
    public static void main(String ... args) {
        SpringApplication.run(HarryKartApp.class, args);
    }
}
