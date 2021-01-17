package com.dakuo.backpack;

import com.dakuo.backpack.command.backpack;
import com.dakuo.backpack.utils.yamlUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Backpack extends JavaPlugin {
    public static Plugin plugin;
    @Override
    public void onEnable() {
        plugin = this;
        outEnableMessage();
        yamlUtils.loadYamlData();
        this.getCommand("openbackpack").setExecutor(new backpack());
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
