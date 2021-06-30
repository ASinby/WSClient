package com.sinby.wsclient.Tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinby.wsclient.Dao.TableDao;
import com.sinby.wsclient.Entity.DetailVO;
import com.sinby.wsclient.Entity.MessageVO;
import com.sinby.wsclient.Entity.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

@Service
public class MessageResolver {

    Logger logger = Logger.getLogger(String.valueOf(MessageResolver.class));

    private final String BOMS = "<SP>";     //电文头
    private final int BOML = BOMS.length(); //电文头长
    private final String EOMS = "</SP>";    //电文尾
    private final int EOML = EOMS.length(); //电文尾长

    private String recvStr = "";    // 收到的字符串
    private String recvMsg = "";    // 收到的完整电文

    private String current_ip = "";

    @Autowired
    private TableDao tableDao;

    /**
     * 启动字符串解析器
     * @param msg
     * @param scalesIp
     */
    public void initResolver(String msg,String scalesIp){
        this.current_ip = scalesIp;
        Object msgObj = getJsonFromMsg(msg);    //校验数据完整性

        if(msgObj != null){
            MessageVO messageVO = JSON.parseObject(msgObj.toString(), MessageVO.class);     //粗略解析数据
            Map<String, DetailVO> map = messageVO.getHr().getData();    //精解析
            insertDataOfMsg(map, scalesIp);
        }
    }

    /**
     * 将有用的重量数据插入数据库中
     * @param param 传入电文
     * @param scalesIp  秤IP
     */
    public void insertDataOfMsg(Map<String, DetailVO> param, String scalesIp){

        TableVO tableVO = new TableVO();

        String onweighter = "0";    //车辆是否上秤     1 -上秤
        String stabilized = "0";    //重量是否稳定    !0 -稳定

        for(String sigName:param.keySet()){
            if(param.get(sigName).getType().equals("OnWeighter")){  //车辆上秤信号
                onweighter = param.get(sigName).getValue();
            }

            if(param.get(sigName).getType().equals("Stabilized")){  //重量稳定信号
                stabilized = param.get(sigName).getValue();
            }
        }

        if(onweighter.equals("1") && !stabilized.equals("0")){   //车辆上秤且重量稳定

            for(String sigName:param.keySet()){

                if(param.get(sigName).getType().equals("Weight")){  //重量
                    tableVO.setWeight(Integer.valueOf(param.get(sigName).getValue()));
                }

                if(param.get(sigName).getType().equals("Vnr")){  //包号

                    tableVO.setLadleNo(param.get(sigName).getValue());
                }

                if(param.get(sigName).getName().indexOf("红外0")!=-1){  //红外0
                    tableVO.setDIO_0(param.get(sigName).getValue());
                }

                if(param.get(sigName).getName().indexOf("红外1")!=-1){  //红外1
                    tableVO.setDIO_1(param.get(sigName).getValue());
                }

            }

            tableVO.setScalesNo(scalesIp);  //设置秤IP

            try {
                int n = tableDao.addTable(tableVO); //插入数据

            } catch (Exception e) {
                e.printStackTrace();
                logger.warning("插入数据异常: "+e.getMessage());
            }

        }
    }

    /**
     * 返回格式化后的消息
     * @param msg
     * @return
     */
    public Object getJsonFromMsg(String msg){

        Object jsonObj = new JSONObject();

        // 消息处理（字符截取)
        recvStr += msg; // 所有未处理部分

        // 查找电文结尾"</SP>"
        int offset = recvStr.indexOf(EOMS);

        if (offset >= 0){

            recvMsg += recvStr.substring(0, offset);    //去尾</SP>

            // 判断一下电文头，确保电文完整性
            if (recvMsg.substring(0, BOML).equals(BOMS)){
                // 去除电文定向
                int pos = recvMsg.indexOf('|');

                //去头<SP>
                if (pos >= 0){
                    recvMsg = recvMsg.substring(pos + 1);
                }else {
                    recvMsg = recvMsg.substring(BOML);
                }

                try {
                    jsonObj = JSON.parseObject(recvMsg);
                }catch (Exception e) {
                    logger.warning("来自"+current_ip+"的电文作废 不是正确的json\n" + msg);
                }

            }else{
                // 电文作废
                logger.warning("来自"+current_ip+"电文作废 不是正确的电文头\n" + msg);
            }

        }else{
            // 电文作废
            logger.warning("来自"+current_ip+"电文作废 不是正确的电文尾\n" + msg);
        }

        // 清理本条电文
        recvStr = "";
        recvMsg = "";

        return jsonObj;
    }

}
