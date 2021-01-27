package com.dakuo.backpack.entity;

public class BackPackEntity {
    public int id;
    public String backpackType;
    public int level;
    public String content;

    public BackPackEntity(int id, String backpackType, int level, String content) {
        this.id = id;
        this.backpackType = backpackType;
        this.level = level;
        this.content = content;
    }

    public BackPackEntity(String backpackType, int level) {
        this.backpackType = backpackType;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackpackType() {
        return backpackType;
    }

    public void setBackpackType(String backpackType) {
        this.backpackType = backpackType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BackPackEntity{" +
                "id=" + id +
                ", backpackType='" + backpackType + '\'' +
                ", level=" + level +
                ", content='" + content + '\'' +
                '}';
    }
}
