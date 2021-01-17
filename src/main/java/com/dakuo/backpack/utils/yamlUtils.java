package com.dakuo.backpack.utils;

import com.dakuo.backpack.Backpack;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class yamlUtils {
    public static Map<String, YamlConfiguration> yamlMap = new HashMap<>();


    public static void loadYamlData(){
        File file = new File(Backpack.plugin.getDataFolder()+"/backpacks");
        Logger logger = Backpack.plugin.getLogger();
        if(!file.exists()){
            logger.info("§a构建目录中...");
            Backpack.plugin.saveDefaultConfig();
            Backpack.plugin.saveResource("backpacks/first.yml",false);
            Backpack.plugin.saveResource("backpacks/second.yml",false);
            logger.info("§a目录构建完毕!");
        }
        logger.info("§a加载背包配置中...");

        int i = 0;
        for (File listFile : file.listFiles()) {
            if(listFile.getName().contains(".yml")){
                yamlMap.put(listFile.getName(),YamlConfiguration.loadConfiguration(listFile));
                i++;
            }
        }
        logger.info("§a背包配置加载成功，共加载了"+i+"个背包配置!");
    }
}
