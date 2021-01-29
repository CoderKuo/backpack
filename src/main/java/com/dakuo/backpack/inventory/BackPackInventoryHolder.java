package com.dakuo.backpack.inventory;

import com.dakuo.backpack.utils.InventoryPage;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BackPackInventoryHolder implements InventoryHolder {
    List<ItemStack> itemStacks = new ArrayList<>();
    int index;
    int id;
    InventoryPage inventoryPage;
    BackPackInventory backPackInventory;
    public BackPackInventoryHolder(List<ItemStack> itemStacks,BackPackInventory backPackInventory,int id){
        this.itemStacks = itemStacks;
        this.backPackInventory = backPackInventory;
        index = 1;
        this.id = id;
        inventoryPage = new InventoryPage(45,itemStacks);
    }

    public List<ItemStack> getItemStacks(){
        return inventoryPage.getItemStack(index);
    }

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
