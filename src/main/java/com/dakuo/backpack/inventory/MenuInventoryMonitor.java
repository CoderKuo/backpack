package com.dakuo.backpack.inventory;

import com.dakuo.backpack.utils.Util;
import com.google.common.base.Strings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MenuInventoryMonitor implements Listener {
    List<Integer> slotList = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);

    @EventHandler
    public void menuInventory(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        boolean a = false;
        if (holder instanceof MenuHolder) {
            a = event.getInventory().getTitle().contains("背包仓库");
        }
        if (!a) {
            return;
        }
        event.setCancelled(true);
        if (event.getRawSlot() < 0 || event.getRawSlot() > event.getInventory().getSize() || event.getInventory() == null) {
            return;
        }


        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null) {
            return;
        }
        if (!currentItem.hasItemMeta()) {
            return;
        }


        if (currentItem.getItemMeta().getDisplayName().contains("§a§l下一页")) {
            MenuHolder holder1 = (MenuHolder) holder;
            holder1.setIndex(holder1.index + 1);
            holder1.menuInventory.OpenInventory();
        }


        if (currentItem.getItemMeta().getDisplayName().contains("§a§l上一页")) {
            MenuHolder holder1 = (MenuHolder) holder;
            holder1.setIndex(holder1.index - 1);
            holder1.menuInventory.OpenInventory();
        }

        if (slotList.contains(event.getRawSlot())) {
            List<String> lore = currentItem.getItemMeta().getLore();
            String s = lore.get(lore.size() - 1);
            String[] split = s.split(":");
            MenuHolder holder1 = (MenuHolder) holder;
            String backPackContentById = holder1.inventoryService.getBackPackContentById(Integer.parseInt(split[1]));
            if (!Strings.isNullOrEmpty(backPackContentById)) {
                List<ItemStack> itemStackByString = Util.getItemStackByString(backPackContentById.split("(fenge)"));
                BackPackInventory backPackInventory = new BackPackInventory((Player) event.getWhoClicked(), Integer.parseInt(split[1]), itemStackByString);
                backPackInventory.openInventory();
            } else {
                BackPackInventory backPackInventory = new BackPackInventory((Player) event.getWhoClicked(), Integer.parseInt(split[1]), new ArrayList<>());
                backPackInventory.openInventory();
            }
        }

        System.out.println(event.getRawSlot());
    }

}
