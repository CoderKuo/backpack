package com.dakuo.backpack.database;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private SqlBase base;

    public Database(SqlBase sqlBase) throws Database.ConnectionException {
        try {
            if(sqlBase.getConnection().isValid(10)){
                throw new Database.ConnectionException("数据库配置异常！");
            }
        }catch (SQLException e){
        }
        this.base = sqlBase;
    }

    public SqlBase getBase() {return base;}

    public Connection getConnection() {
        return this.base.getConnection();
    }



    public static class ConnectionException extends Exception{
        public ConnectionException(String msg){
            super(msg);
        }
    }
}
