package com.dakuo.backpack.utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI分页工具类
 * index从1开始
 */
public class InventoryPage {
    int pageSize;
    List<ItemStack> list = new ArrayList<>();

    public InventoryPage(int pageSize, List<ItemStack> list) {
        this.pageSize = pageSize;
        this.list = list;
    }

    public List<ItemStack> getItemStack(int index){
        if(list.size()<=pageSize){
            return list;
        }
        int firstIndex = (index-1)*pageSize;
        int lastIndex = firstIndex + pageSize;
        if(lastIndex >= list.size()){
            lastIndex = list.size();
        }
        return list.subList(firstIndex,lastIndex);
    }

}
