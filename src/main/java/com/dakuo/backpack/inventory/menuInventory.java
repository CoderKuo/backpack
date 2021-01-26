package com.dakuo.backpack.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class menuInventory {
    public void OpenInventory(Player player){
        Inventory inventory = Bukkit.createInventory(new MyHolder(), 54, "§8§l背包仓库");
        Map<Integer, ItemStack> itemStackBk = getItemStackBk();
        for (Integer integer : itemStackBk.keySet()) {
            inventory.setItem(integer,itemStackBk.get(integer));
        }



        player.openInventory(inventory);
    }

    private Map<Integer, ItemStack> getItemStackBk(){
        Map<Integer,ItemStack> map = new HashMap<>();
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        itemStack.setDurability((short) 1);
        for (int i = 0; i < 54; i++) {
            if(i < 9){
                map.put(i,itemStack);
            }else if(i > 44){
                if(i != 47 && i != 51){
                    map.put(i,itemStack);
                }
            }
        }
        return map;
    }
}
