package com.dakuo.backpack.utils;

import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.database.ApiImpl;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<ItemStack> getItemStackByString(String[] strings){
        ApiImpl api = new ApiImpl();
        List<ItemStack> itemStacks = new ArrayList<>();
        try {
            for (String string : strings) {
                ItemStack itemStack = api.singleObjectFromString(string, ItemStack.class);
                itemStacks.add(itemStack);
            }
        }catch (Exception e){
            e.printStackTrace();
            Backpack.plugin.getLogger().warning("§c§l背包数据出现异常！");
        }
        return itemStacks;
    }
}
