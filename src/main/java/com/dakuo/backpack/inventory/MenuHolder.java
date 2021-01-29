package com.dakuo.backpack.inventory;

import com.dakuo.backpack.entity.BackPackEntity;
import com.dakuo.backpack.service.InventoryService;
import com.dakuo.backpack.utils.InventoryPage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuHolder implements InventoryHolder {
    Map<Integer, BackPackEntity> map = new HashMap<>();
    int index;
    Player player;
    InventoryService inventoryService;
    InventoryPage inventoryPage;
    MenuInventory menuInventory;
    List<BackPackEntity> BackPackList = new ArrayList<>();
    List<ItemStack> itemStacks = new ArrayList<>();

    public MenuHolder(Player player, MenuInventory menuInventory){
        inventoryService = InventoryService.getInstance();
        this.menuInventory = menuInventory;
        this.player = player;
        BackPackList = inventoryService.getBackPackListByPlayerUUID(player.getUniqueId().toString());
        itemStacks = inventoryService.showBackItemStacks(BackPackList);
        inventoryPage = new InventoryPage(28,itemStacks);
        index = 1;
    }


    public List<ItemStack> getItemStackByIndex(){
        return inventoryPage.getItemStack(index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Map<Integer, BackPackEntity> getMap() {
        return map;
    }

    public void setMap(Map<Integer, BackPackEntity> map) {
        this.map = map;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
