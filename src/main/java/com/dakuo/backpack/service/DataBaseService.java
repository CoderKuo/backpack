package com.dakuo.backpack.service;

import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.database.SqlBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseService {
    SqlBase sqlBase;



    public DataBaseService(SqlBase sqlBase){
        this.sqlBase = sqlBase;
    }

    /**
     * 检查数据库中是否有某个表
     * @param table 表名
     * @return 是否有某个表
     */
    public boolean hasTable(String table){
        Statement stmt = null;
        Connection connection = null;
        ResultSet rs = null;
        try{
            connection = sqlBase.getConnection();
            stmt = connection.createStatement();
            DatabaseMetaData md = stmt.getConnection().getMetaData();
            rs = md.getTables((String) null, (String) null, table, (String[]) null);
            while (rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlBase.close(rs,stmt,connection);
        }
        return false;
    }

    /**
     * 创建基础表
     * @param name 基础表名称
     */
    public void createTableBase(String name){
        if(name.equalsIgnoreCase("backpack_data")){
            sqlBase.queue(new BufferStatement("create table IF NOT EXISTS `backpack_data` ( " +
                    "`id` int(11) not null AUTO_INCREMENT," +
                    "`backpackType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`level` int(11) NULL DEFAULT NULL," +
                    "`content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL," +
                    "PRIMARY KEY (`id`)" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;"));
        }else if(name.equalsIgnoreCase("backpack_player_data")){
            sqlBase.queue(new BufferStatement("CREATE TABLE IF NOT EXISTS `backpack_player_data`  (\n" +
                    "  `player_uuid` varchar(255) NOT NULL primary key,\n" +
                    "  `backpacks` varchar(255))"));
        }
        sqlBase.flush();
    }
}
