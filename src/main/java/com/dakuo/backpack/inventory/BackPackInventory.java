package com.dakuo.backpack.inventory;

import com.dakuo.backpack.entity.BackPackEntity;
import com.dakuo.backpack.entity.BackPackYamlEntity;
import com.dakuo.backpack.service.InventoryService;
import com.dakuo.backpack.utils.yamlDao;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dakuo.backpack.utils.InventoryUtils.getPageItemStack;

public class BackPackInventory {

    Player player;
    BackPackInventoryHolder backPackInventoryHolder;
    int id;
    String BackPackName;
    InventoryService inventoryService;
    List<ItemStack> itemStacks = new ArrayList<>();

    public BackPackInventory(Player player, int id, List<ItemStack> itemStacks) {
        this.player = player;
        this.itemStacks = itemStacks;
        backPackInventoryHolder = new BackPackInventoryHolder(itemStacks,this,id);
        inventoryService = InventoryService.getInstance();
        this.id = id;
        String backPackTypeById = inventoryService.getBackPackTypeById(id);
        BackPackYamlEntity backPackYamlEntity = yamlDao.BackPackYamlMap.get(backPackTypeById);
        BackPackName = backPackYamlEntity.getName();
    }

    public void openInventory() {
        Inventory inventory = Bukkit.createInventory(backPackInventoryHolder, 54, "§6§l[" + BackPackName + "] 第" + backPackInventoryHolder.index + "页");
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        itemMeta.setDisplayName("-");
        itemStack.setItemMeta(itemMeta);
        for (int i = 45; i < 54; i++) {
            inventory.setItem(i, itemStack);
        }
        itemStack.setDurability((short) 15);
        itemMeta.setDisplayName("§7§l返回仓库");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(49, itemStack);

        List<ItemStack> itemStacks = backPackInventoryHolder.getItemStacks();

        for (ItemStack stack : itemStacks) {
            inventory.setItem(inventory.firstEmpty(), stack);
        }


        if (backPackInventoryHolder.index == 1) {
            ItemStack pageItemStack = getPageItemStack(InventoryItemStack.RedLastItem);
            inventory.setItem(45, pageItemStack);
            inventory.setItem(46, pageItemStack);
            inventory.setItem(47, pageItemStack);
            inventory.setItem(48, pageItemStack);
            if (inventory.firstEmpty() == -1) {
                ItemStack pageItemStack1 = getPageItemStack(InventoryItemStack.GreenNextItem);
                inventory.setItem(50, pageItemStack1);
                inventory.setItem(51, pageItemStack1);
                inventory.setItem(52, pageItemStack1);
                inventory.setItem(53, pageItemStack1);
            } else {
                ItemStack pageItemStack1 = getPageItemStack(InventoryItemStack.RedNextItem);
                inventory.setItem(50, pageItemStack1);
                inventory.setItem(51, pageItemStack1);
                inventory.setItem(52, pageItemStack1);
                inventory.setItem(53, pageItemStack1);
            }
        } else {
            if (inventory.firstEmpty() == -1) {
                ItemStack pageItemStack1 = getPageItemStack(InventoryItemStack.GreenNextItem);
                inventory.setItem(50, pageItemStack1);
                inventory.setItem(51, pageItemStack1);
                inventory.setItem(52, pageItemStack1);
                inventory.setItem(53, pageItemStack1);
                ItemStack pageItemStack = getPageItemStack(InventoryItemStack.GreenLastItem);
                inventory.setItem(45, pageItemStack);
                inventory.setItem(46, pageItemStack);
                inventory.setItem(47, pageItemStack);
                inventory.setItem(48, pageItemStack);
            } else {
                ItemStack pageItemStack = getPageItemStack(InventoryItemStack.GreenLastItem);
                inventory.setItem(45, pageItemStack);
                inventory.setItem(46, pageItemStack);
                inventory.setItem(47, pageItemStack);
                inventory.setItem(48, pageItemStack);
                ItemStack pageItemStack1 = getPageItemStack(InventoryItemStack.RedNextItem);
                inventory.setItem(50, pageItemStack1);
                inventory.setItem(51, pageItemStack1);
                inventory.setItem(52, pageItemStack1);
                inventory.setItem(53, pageItemStack1);
            }

        }

        player.openInventory(inventory);
    }

}
