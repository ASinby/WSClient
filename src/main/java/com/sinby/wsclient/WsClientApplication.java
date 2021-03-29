package com.sinby.wsclient;

import com.sinby.wsclient.WebSocketClient.StartWebSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;

@SpringBootApplication
public class WsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsClientApplication.class, args);

//        SpringApplication springApplication = new SpringApplication(WsClientApplication.class);
//        springApplication.setBannerMode(Banner.Mode.OFF); //关闭显示banner
//        springApplication.run(args);
    }

}
