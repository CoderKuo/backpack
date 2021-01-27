package com.dakuo.backpack.entity;

import com.alibaba.fastjson.JSONObject;

public class BackPackYamlEntity {
    String name;
    String description;
    int size;
    JSONObject level;

    public BackPackYamlEntity() {
    }

    public BackPackYamlEntity(String name, String description, int size, JSONObject level) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public JSONObject getLevel() {
        return level;
    }

    public void setLevel(JSONObject level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "BackPackYamlEntity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", size=" + size +
                ", level=" + level.toJSONString() +
                '}';
    }
}
