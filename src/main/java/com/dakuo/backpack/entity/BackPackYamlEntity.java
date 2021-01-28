package com.dakuo.backpack.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class BackPackYamlEntity {
    String name;
    List<String> description;
    int size;
    JSONObject material;
    JSONObject level;

    public BackPackYamlEntity() {
    }

    public BackPackYamlEntity(String name, List<String> description, int size,JSONObject material, JSONObject level) {
        this.name = name;
        this.description = description;
        this.size = size;
        this.level = level;
        this.material = material;
    }

    public JSONObject getMaterial() {
        return material;
    }

    public void setMaterial(JSONObject material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
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
                ", material='" + material.toJSONString() + '\'' +
                ", level=" + level.toJSONString() +
                '}';
    }
}
