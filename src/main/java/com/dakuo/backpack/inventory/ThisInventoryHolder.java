package com.dakuo.backpack.inventory;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public interface ThisInventoryHolder {
    public Map<Integer, ItemStack> getItemStackBk();
}
