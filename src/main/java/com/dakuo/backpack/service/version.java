package com.dakuo.backpack.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.dakuo.backpack.Backpack;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class version {
    public static List<String> getVersionMsg(){
        List<String> versionMsg = new ArrayList<>();
        String version = "获取失败！";
        try {
            Path tempFile = Files.createTempFile("version", ".yml");
            Files.copy(new URL("https://cdn.jsdelivr.net/gh/dkinging/backpack@master/version.yml").openStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            tempFile.toFile().deleteOnExit();
            String str = new String(Files.readAllBytes(tempFile), "UTF-8");
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration.loadFromString(str);
            version = yamlConfiguration.getString("version");
        }catch (Exception e){
            e.printStackTrace();
        }
        versionMsg.add("§6===========[BackPack]============");
        if(Backpack.plugin.getDescription().getVersion().equalsIgnoreCase(version)){
            versionMsg.add("欢迎您使用BackPack最新版本! 版本号:"+version);
            return versionMsg;
        }
        versionMsg.add("BackPack不是最新版本!最新版本: §b"+version+"§6!你的版本: §b"+Backpack.plugin.getDescription().getVersion());
        return versionMsg;
    }
}
