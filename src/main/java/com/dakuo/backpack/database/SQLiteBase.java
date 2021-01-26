package com.dakuo.backpack.database;

import com.dakuo.backpack.Backpack;

import java.io.File;
import java.sql.*;
import java.util.LinkedList;

public class SQLiteBase implements SqlBase{

    private String url;
    private volatile LinkedList<BufferStatement> queue = new LinkedList();
    private volatile Thread watcher;
    private File file;

    public SQLiteBase(File file){
        url = "jdbc:sqlite:"+file;
        this.file = file;
    }

    public Connection getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            Backpack.getPlugin(Backpack.class).getLogger().warning("§c§l>>>SQLite数据库连接失败!");
            e.printStackTrace();
        }
        return null;
    }

    public void queue(BufferStatement bs) {
        synchronized(this.queue) {
            this.queue.add(bs);
        }

        if (this.watcher == null || !this.watcher.isAlive()) {
            this.startWatcher();
        }

    }

    public void flush() {
        while(!this.queue.isEmpty()) {
            BufferStatement bs;
            synchronized(this.queue) {
                bs = (BufferStatement)this.queue.removeFirst();
            }

            synchronized(this.file) {
                try {
                    PreparedStatement ps = bs.preparedStatement(this.getConnection());
                    ps.execute();
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
        this.flush();

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
        this.flush();
    }

    private void startWatcher() {
        this.watcher = new Thread() {
            public void run() {
                try {
                    Thread.sleep(30000L);
                } catch (InterruptedException var2) {
                }

                SQLiteBase.this.flush();
            }
        };
        this.watcher.start();
    }

}
