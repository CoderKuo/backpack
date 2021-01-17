package com.dakuo.backpack;

import com.dakuo.backpack.command.commandHandler;
import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.database.MysqlBase;
import com.dakuo.backpack.database.SQLiteBase;
import com.dakuo.backpack.database.SqlBase;
import com.dakuo.backpack.utils.yamlUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Backpack extends JavaPlugin {
    public static Plugin plugin;
    public static SqlBase sqlBase;
    @Override
    public void onEnable() {
        plugin = this;
        outEnableMessage();
        yamlUtils.loadYamlData();
        this.getCommand("bp").setExecutor(new commandHandler());

        if(getConfig().getBoolean("database.mysql")){
            ConfigurationSection database = getConfig().getConfigurationSection("database");
            String host = database.getString("host");
            String port = database.getString("port");
            String database1 = database.getString("database");
            String user = database.getString("user");
            String password = database.getString("password");
            boolean usessl = database.getBoolean("usessl");
            sqlBase = new MysqlBase(host,user,password,database1,port,usessl);
            getLogger().info("§a存储类型为Mysql");
        }else{
            File data = new File(getDataFolder() + "/data.db");
            sqlBase = new SQLiteBase(data);
            getLogger().info("§a存储类型为SQLite");
            if(!data.exists()){
                sqlBase.queue(new BufferStatement("CREATE TABLE `backpack_data`  (\n" +
                        "  `id` int(11) NOT NULL ,\n" +
                        "  `player_uuid` varchar(255) NOT NULL,\n" +
                        "  `level` int(11) NULL ,\n" +
                        "  `content` text ,\n" +
                        "  PRIMARY KEY (`id`) \n" +
                        ") "));
                sqlBase.flush();
            }
        }
    }

    @Override
    public void onDisable() {

    }


    private void outEnableMessage(){
        System.out.println("§1==============================");
        System.out.println("");
        System.out.println("§a  Backpack启动成功！");
        System.out.println("");
        System.out.println("§e  感谢使用Backpack背包插件 :)");
        System.out.println("§e  当前插件版本为:"+getDescription().getVersion());
        System.out.println("§e  当前插件最新版本为:");
        System.out.println("");
        System.out.println("§1==============================");
    }
}
