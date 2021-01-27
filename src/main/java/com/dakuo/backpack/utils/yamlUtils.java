package com.dakuo.backpack.utils;

import com.alibaba.fastjson.JSONObject;
import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.entity.BackPackYamlEntity;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
        loadYamlToMap();
        logger.info("§a背包配置加载成功，共加载了"+i+"个背包配置!");
    }

    public static void loadYamlToMap(){
        for (String s : yamlMap.keySet()) {
            YamlConfiguration yamlConfiguration = yamlMap.get(s);
            BackPackYamlEntity backPackYamlEntity = new BackPackYamlEntity();
            backPackYamlEntity.setName(yamlConfiguration.getString("name"));
            backPackYamlEntity.setDescription(yamlConfiguration.getString("description"));
            backPackYamlEntity.setSize(yamlConfiguration.getInt("size"));
            if(yamlConfiguration.getKeys(false).contains("level")){
                ConfigurationSection level = yamlConfiguration.getConfigurationSection("level");
                Set<String> keys = level.getKeys(false);
                JSONObject jsonObject = new JSONObject();
                for (String key : keys) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("size",level.getInt("size"));
                    ConfigurationSection configurationSection = level.getConfigurationSection(key);
                    if(configurationSection.getKeys(false).contains("cost")){
                        ConfigurationSection cost = configurationSection.getConfigurationSection("cost");
                        Set<String> keys1 = cost.getKeys(false);
                        for (String s1 : keys1) {
                            JSONObject jsonObject2 = new JSONObject();
                            jsonObject2.put(s1,cost.getInt(s1));
                            jsonObject1.put("cost",jsonObject2);
                        }
                    }
                    jsonObject.put(key,jsonObject1);
                }
                backPackYamlEntity.setLevel(jsonObject);
            }
            yamlDao.BackPackYamlMap.put(s.replace(".yml",""),backPackYamlEntity);
        }

        for (String s : yamlDao.BackPackYamlMap.keySet()) {
            System.out.println(yamlDao.BackPackYamlMap.get(s).toString());
        }

    }
}
