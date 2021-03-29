package com.sinby.wsclient.WebSocketClient;

import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.*;
import java.util.logging.Logger;

@Component
public class StartWebSocket {

    Logger logger = Logger.getLogger(String.valueOf(StartWebSocket.class));

    /**
     * 在application.properties文件中配置服务器地址
     */
    @Value("${websocket.server.ip}")
    private String IPS;

    @Value("${websocket.server.method}")
    private String METHOD;

    @Value("${websocket.user.name}")
    private String USERNAME;

    private Set<String> UNITED_URI = new HashSet<>();   //服务器地址
    /**
     * @PostConstruct 关键注解
     */
    @PostConstruct
    public void initWebSocketClient(){

        for(String ip:IPS.split(",")){
            ip = "ws://"+ip+"/"+METHOD+"?user="+USERNAME;
            UNITED_URI.add(ip);
        }

        for(String serveruri:UNITED_URI){
            connectServer(serveruri);
        }

/*        // get name representing the running Java virtual machine.
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);

        // get pid
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);*/
    }

    public void connectServer(String serverUri){

        String server_ip = serverUri.split("/")[2]; //服务器IP

        URI uri = URI.create(serverUri);
        MyWebSocketClient myClient = new MyWebSocketClient(uri);

        try {
            myClient.settingCurrentUri(server_ip);    //取秤的IP地址
            myClient.connect();

            logger.info("====== "+server_ip+" 连接中... ======");
            Thread.sleep(2000); //连接缓存时间

            if (!myClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {   //将连接成功的服务器地址从UNUNITED_URI移出
                logger.warning("====== "+server_ip+" 初次连接失败!尝试连接中... ======");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
