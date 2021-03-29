package com.sinby.wsclient.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVO {

    //sig_weight
    Boolean SaveData;
    Integer MaxInterval;

    //sig_onweighter sig_stabilized
    Integer MinWeight;
    Integer Duration;

    //sig_stabilized
    Integer FloatingRange;

    //sig_DIO_0     sig_DIO_1   sig_DIO_4   sig_DIO_6
    String IO;
    Integer Channel;
    Boolean Reverse;

    //sig_trucknumber
    Boolean Color;
    Boolean Trailer;

    //各类共用
    String Value;
    String Name;
    String Id;
    String DataSource;
    String Type;
    String Simulator;
    String Status;
}
