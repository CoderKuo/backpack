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
        if (holder instanceof MyHolder) {
            a = event.getInventory().getTitle().contains("背包仓库");
        }

        if (a) {
            event.setCancelled(true);
        }
        if (event.getRawSlot() < 0 || event.getRawSlot() > event.getInventory().getSize() || event.getInventory() == null) {
            return;
        }

    }

}
