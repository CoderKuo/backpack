package com.dakuo.backpack.database;

import com.dakuo.backpack.Backpack;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void close() {
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
