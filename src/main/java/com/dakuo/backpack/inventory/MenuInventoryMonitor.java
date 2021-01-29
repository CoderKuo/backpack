package com.dakuo.backpack.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MenuInventoryMonitor implements Listener {


    @EventHandler
    public void menuInventory(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        boolean a = false;
        if (holder instanceof MenuHolder) {
            a = event.getInventory().getTitle().contains("背包仓库");
        }

        if (a) {
            event.setCancelled(true);
        }
        if (a && event.getRawSlot() < 0 || event.getRawSlot() > event.getInventory().getSize() || event.getInventory() == null) {
            return;
        }
        if(!a){
            return;
        }

        ItemStack currentItem = event.getCurrentItem();
        if(currentItem == null){
            return;
        }
        if(!currentItem.hasItemMeta()){
            return;
        }


        if(currentItem.getItemMeta().getDisplayName().contains("§a§l下一页")){
            MenuHolder holder1 = (MenuHolder) holder;
            holder1.setIndex(holder1.index+1);
            holder1.menuInventory.OpenInventory();
        }


        if(currentItem.getItemMeta().getDisplayName().contains("§a§l上一页")){
            MenuHolder holder1 = (MenuHolder) holder;
            holder1.setIndex(holder1.index-1);
            holder1.menuInventory.OpenInventory();
        }
    }

}
