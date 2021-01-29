package com.dakuo.backpack.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BackPackInventory {

    public void openInventory(Player player,String backpackName,int index){
        MyHolder myHolder = new MyHolder();
        myHolder.setIndex(index);
        Inventory inventory = Bukkit.createInventory(myHolder, 54, "§e§l["+backpackName+"]");



    }
}
