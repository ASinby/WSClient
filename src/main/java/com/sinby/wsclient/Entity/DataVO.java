package com.sinby.wsclient.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataVO {

//    Object sig_weight;  //衡器仪表值
//    Object sig_onweighter;  //车辆上秤
//    Object sig_stabilized;  //重量稳定
//    Object sig_DIO_0;   //红外(左)
//    Object sig_DIO_1;   //雷达(右)
//    Object sig_DIO_4;   //信号灯(右)
//    Object sig_DIO_6;   //系统启停
//    Object sig_trucknumber; //车号识别
    String sigName;
    DetailVO detailVO;
}
