package org.seerbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeerBitApplication {
    public static void main(String[] args) {

        SpringApplication.run(SeerBitApplication.class, args);

        System.out.println("Hello, World!");
    }
}