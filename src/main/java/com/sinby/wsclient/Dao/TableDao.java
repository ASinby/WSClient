package com.sinby.wsclient.Dao;

import com.sinby.wsclient.Entity.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class TableDao {

    @Value("${websocket.server.ip}")
    private String IPS;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 插入重量信息
     * @param tableVO   重量信息
     * @return
     */
    public int addTable(TableVO tableVO){

        String[] arr = IPS.split(",");
        int positon = Arrays.binarySearch(arr, tableVO.getScalesNo())+1;  //轨道号

        return jdbcTemplate.update("INSERT INTO WLUSER.LADLE_WEIGHT_SIGNAL(SCALES_NO, LADLE_NO, WEIGHT, DIO_0, DIO_1, PATHWAY_NO) VALUES(?, ?, ?, ?, ?, ?)",
                tableVO.getScalesNo(), tableVO.getLadleNo(), tableVO.getWeight(), tableVO.getDIO_0(), tableVO.getDIO_1(), positon);
    }
}
