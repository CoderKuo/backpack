package com.dakuo.backpack.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;


public class MyHolder implements InventoryHolder {
     int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
