package com.dakuo.backpack.utils;

import com.dakuo.backpack.inventory.InventoryItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtils {
    public static ItemStack getPageItemStack(InventoryItemStack inventoryItemStack) {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        switch (inventoryItemStack) {
            case RedLastItem:
                itemStack.setDurability((short) 14);
                itemMeta.setDisplayName("§c§l上一页");
                itemStack.setItemMeta(itemMeta);
                break;
            case RedNextItem:
                itemStack.setDurability((short) 14);
                itemMeta.setDisplayName("§c§l下一页");
                itemStack.setItemMeta(itemMeta);
                break;
            case GreenLastItem:
                itemStack.setDurability((short) 5);
                itemMeta.setDisplayName("§a§l上一页");
                itemStack.setItemMeta(itemMeta);
                break;
            case GreenNextItem:
                itemStack.setDurability((short) 5);
                itemMeta.setDisplayName("§a§l下一页");
                itemStack.setItemMeta(itemMeta);
                break;
        }
        return itemStack;
    }


}
