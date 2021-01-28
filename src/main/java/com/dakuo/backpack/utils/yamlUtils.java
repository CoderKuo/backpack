package com.dakuo.backpack.utils;

import com.alibaba.fastjson.JSONObject;
import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.entity.BackPackYamlEntity;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

public class yamlUtils {
    public Map<String, YamlConfiguration> yamlMap = new HashMap<>();

    private static yamlUtils instance = new yamlUtils();

    private yamlUtils(){}

    public static yamlUtils getInstance(){
        return instance;
    }

    public void loadYamlData(){
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
        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            if(listFile.getName().contains(".yml")){
                yamlMap.put(listFile.getName(),YamlConfiguration.loadConfiguration(listFile));
                i++;
            }
        }
        loadYamlToMap();
        logger.info("§a背包配置加载成功，共加载了"+i+"个背包配置!");
    }

    public void loadYamlToMap(){
        for (String s : yamlMap.keySet()) {
            YamlConfiguration yamlConfiguration = yamlMap.get(s);
            BackPackYamlEntity backPackYamlEntity = new BackPackYamlEntity();
            backPackYamlEntity.setName(yamlConfiguration.getString("name"));
            backPackYamlEntity.setDescription(yamlConfiguration.getStringList("description"));
            backPackYamlEntity.setSize(yamlConfiguration.getInt("size"));
            if(yamlConfiguration.getKeys(false).contains("type")){
                JSONObject type = getTypeConfig(yamlConfiguration.getConfigurationSection("type"));
                backPackYamlEntity.setMaterial(type);
            }
            if(yamlConfiguration.getKeys(false).contains("level")){
                ConfigurationSection level = yamlConfiguration.getConfigurationSection("level");
                JSONObject levelConfig = getLevelConfig( level);
                backPackYamlEntity.setLevel(levelConfig);
            }
            yamlDao.BackPackYamlMap.put(s.replace(".yml",""),backPackYamlEntity);
        }

        for (String s : yamlDao.BackPackYamlMap.keySet()) {
            System.out.println(yamlDao.BackPackYamlMap.get(s).toString());
        }

    }

    private JSONObject getCommands(ConfigurationSection commands){
        Set<String> keys = commands.getKeys(false);
        JSONObject jsonObject = new JSONObject(false);
        if(keys.contains("PlayerCommands")){
            jsonObject.put("PlayerCommands",commands.getStringList("PlayerCommands"));
        }
        if(keys.contains("OpCommands")){
            jsonObject.put("OpCommands",commands.getStringList("OpCommands"));
        }
        if(keys.contains("ConsoleCommands")){
            jsonObject.put("ConsoleCommands",commands.getStringList("ConsoleCommands"));
        }
        return jsonObject;
    }

    private JSONObject getExtend(ConfigurationSection extend){
        Set<String> keys = extend.getKeys(false);
        JSONObject jsonObject = new JSONObject(false);
        if(keys.contains("commands")){
            JSONObject commands = getCommands(extend.getConfigurationSection("commands"));
            jsonObject.put("commands",commands);
        }
        return jsonObject;
    }

    private JSONObject getTypeConfig(ConfigurationSection type){
        JSONObject jsonObject = new JSONObject(false);
        ConfigurationSection material = type.getConfigurationSection("Material");
        JSONObject materialJson = new JSONObject(false);
        materialJson.put("name",material.getString("name"));
        materialJson.put("id",material.getInt("id"));
        jsonObject.put("Material",materialJson);
        jsonObject.put("enchantment",type.getBoolean("enchantment"));
        return jsonObject;
    }

    private JSONObject getCostConfig(ConfigurationSection cost){
        Set<String> keys = cost.getKeys(false);
        JSONObject jsonObject = new JSONObject(false);
        if (keys.contains("money")){
            jsonObject.put("money",cost.getInt("money"));
        }
        if(keys.contains("permission")){
            jsonObject.put("permission",cost.getString("permission"));
        }
        if(keys.contains("points")){
            jsonObject.put("points",cost.getString("points"));
        }
        return jsonObject;
    }

    private JSONObject getLevelConfig(ConfigurationSection level){
        Set<String> keys = level.getKeys(false);
        JSONObject jsonObject = new JSONObject(true);
        for (String key : keys) {
            JSONObject jsonObject1 = new JSONObject(true);
            jsonObject1.put("size",level.getInt("size"));
            ConfigurationSection configurationSection = level.getConfigurationSection(key);
            if(configurationSection.getKeys(false).contains("cost")){
                ConfigurationSection cost = configurationSection.getConfigurationSection("cost");
                JSONObject costConfig = getCostConfig(cost);
                jsonObject.put("cost",costConfig);
            }
            if(configurationSection.getKeys(false).contains("type")){
                ConfigurationSection type = configurationSection.getConfigurationSection("type");
                JSONObject typeConfig = getTypeConfig(type);
                jsonObject.put("type",typeConfig);
            }
            if(configurationSection.getKeys(false).contains("extend")){
                ConfigurationSection extend = configurationSection.getConfigurationSection("extend");
                JSONObject extend1 = getExtend(extend);
                jsonObject.put("extend",extend1);
            }
            jsonObject.put(key,jsonObject1);
        }
        return jsonObject;
    }
}
