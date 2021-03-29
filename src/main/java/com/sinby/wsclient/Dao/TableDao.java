package com.sinby.wsclient.Dao;

import com.sinby.wsclient.Entity.TableVO;
import com.sinby.wsclient.Tool.ApplicationContextHelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

@Repository
public class TableDao {

    Logger logger = Logger.getLogger(String.valueOf(TableDao.class));

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int addTable(TableVO tableVO){

//        logger.info("==== "+jdbcTemplate+" ====");

        return jdbcTemplate.update("INSERT INTO WLUSER.LADLE_WEIGHT_SIGNAL(SCALES_NO, LADLE_NO, WEIGHT, DIO_0, DIO_1) VALUES(?, ?, ?, ?, ?)",
                tableVO.getScalesNo(), tableVO.getLadleNo(), tableVO.getWeight(), tableVO.getDIO_0(), tableVO.getDIO_1());
    }
}
