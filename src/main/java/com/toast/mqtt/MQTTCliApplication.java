package com.toast.mqtt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MQTTCliApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MQTTCliApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MQTT CLI 应用已启动！");
        // 这里可以添加您的 MQTT CLI 业务逻辑
    }
}