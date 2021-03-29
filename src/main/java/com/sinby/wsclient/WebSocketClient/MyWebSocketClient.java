package com.sinby.wsclient.WebSocketClient;

import com.sinby.wsclient.Dao.TableDao;
import com.sinby.wsclient.Tool.ApplicationContextHelperUtil;
import com.sinby.wsclient.Tool.MessageResolver;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.logging.Logger;

public class MyWebSocketClient extends WebSocketClient {

    Logger logger = Logger.getLogger(String.valueOf(MyWebSocketClient.class));

    ApplicationContextHelperUtil applicationContextHelperUtil = new ApplicationContextHelperUtil();

    String current_uri; //当前连接服务器IP

    MyWebSocketClient wsInstance;

    //获取当前连接服务器的IP
    public void settingCurrentUri(String uri){
        current_uri = uri;
    }

    //构造函数
    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public MyWebSocketClient getWsInstance(URI serveruri){
        if(wsInstance == null){
            synchronized (MyWebSocketClient.class){
                wsInstance = new MyWebSocketClient(serveruri);
            }
        }

        return wsInstance;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

//        logger.info("------ MyWebSocket onOpen ------");
//        logger.info("serverHandshake:::" + serverHandshake);
        logger.info("====== "+current_uri+" 连接成功! ======");
    }

    @Override
    public void onClose(int i, String s, boolean b) {

//        logger.info("------ MyWebSocket onClose ------");
//        logger.info("i = " + i + " ### s = " + s +" ### b = " + b);

        //断线自动连接
        logger.warning("====== 与"+this.current_uri+"断开连接 ======");
        String serverIp = this.getURI().toString().split("/")[2];
        URI uri = URI.create(this.getURI().toString());
        MyWebSocketClient myClient = getWsInstance(uri);

        try {
            myClient.settingCurrentUri(serverIp);    //设置秤的IP地址

            while (!myClient.getReadyState().equals(READYSTATE.OPEN)) {   // 判断是否连接成功，若未连接成功则尝试连接
                logger.warning("====== 5秒后尝试再连接 "+serverIp+" ======");

                if(myClient.getReadyState().equals(READYSTATE.NOT_YET_CONNECTED)){
                    myClient.connect();
                }else if(myClient.getReadyState().equals(READYSTATE.CLOSING) || myClient.getReadyState().equals(READYSTATE.CLOSED)){
                    myClient.reconnect();
                }
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
//            e.printStackTrace();
            if(!this.getReadyState().equals(READYSTATE.OPEN)){
                logger.warning("======= "+serverIp+" 重新连接失败，正在尝试连接 =======");
            }

        }

    }

    @Override
    public void onMessage(String s) {

//        logger.info("-------- 接收到服务端数据： " + s + "--------");
        MessageResolver messageResolver = applicationContextHelperUtil.getApplicationContext().getBean(MessageResolver.class);
        try {
            messageResolver.initResolver(s,this.current_uri);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning("-------- " + this.current_uri+ " > " +e.getMessage() + "--------");
        }
    }

    @Override
    public void onError(Exception e) {
        logger.warning("------ 连接"+this.current_uri+"发生错误，错误信息: "+e.getMessage());
    }
}
