package com.dakuo.backpack.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;


public class MyHolder implements InventoryHolder,ThisInventoryHolder {
    private static Map<Integer, ItemStack> itemStackBk = new HashMap<>();

    public Map<Integer,ItemStack> getItemStackBk() { return itemStackBk; }


    @Override
    public Inventory getInventory() {
        return null;
    }
}
