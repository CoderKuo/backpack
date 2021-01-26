package com.dakuo.backpack.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class MysqlBase implements SqlBase{
    private String url;
    private Properties info = new Properties();
    private static ArrayList<Connection> pool = new ArrayList<>();

    public MysqlBase(String host,String user,String pass,String database,String port,boolean ssl){
        this.info.put("autoReconnect", "true");
        this.info.put("user", user);
        this.info.put("password", pass);
        this.info.put("useUnicode", "true");
        this.info.put("characterEncoding", "utf8");
        this.info.put("useSSL", ssl);

        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        for(int i = 0; i < 8; ++i) {
            pool.add((Connection) null);
        }
    }


    @Override
    public Connection getConnection() {

        int i = 0;

        while(i < 8) {
            Connection connection = (Connection)pool.get(i);

            try {
                if (connection != null && !connection.isClosed() && connection.isValid(10)) {
                    return connection;
                }

                connection = DriverManager.getConnection(this.url, this.info);
                pool.set(i, connection);
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
                ++i;
            }
        }

        return null;
    }

    public void queue(BufferStatement bs) {
        try {
            Connection con;
            for(con = this.getConnection(); con == null; this.getConnection()) {
                try {
                    Thread.sleep(15L);
                } catch (InterruptedException e) {
                }
            }

            PreparedStatement ps = bs.preparedStatement(con);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //关闭连接
    public void close(ResultSet rs,PreparedStatement ptmt,Connection conn){
        try {
            if(rs!=null)rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if(ptmt!=null)ptmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if(conn!=null)conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //关闭连接
    public void close(ResultSet rs, Statement ptmt, Connection conn){
        try {
            if(rs!=null)rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if(ptmt!=null)ptmt.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        try {
            if(conn!=null)conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void flush() {
    }
}
