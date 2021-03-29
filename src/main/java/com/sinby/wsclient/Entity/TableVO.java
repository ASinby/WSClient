package com.sinby.wsclient.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TableVO {

    Integer weight; //重量
    String ladleNo; //包号
    String DIO_0;   //红外（左）
    String DIO_1;   //红外（右）
    String tm;      //记录时间
    String scalesNo;   //秤号
    Integer readFlag;   //记录是否被读状态
}
