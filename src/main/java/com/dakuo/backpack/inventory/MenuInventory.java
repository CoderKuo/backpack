package com.dakuo.backpack.inventory;

import com.dakuo.backpack.entity.BackPackEntity;
import com.dakuo.backpack.service.InventoryService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dakuo.backpack.utils.InventoryUtils.getPageItemStack;

public class MenuInventory {

    InventoryService inventoryService;
    MenuHolder menuHolder;
    Player player;

    public MenuInventory(Player player) {
        inventoryService = InventoryService.getInstance();
        menuHolder = new MenuHolder(player, this);
        this.player = player;
    }

    public void OpenInventory() {
        Inventory inventory = Bukkit.createInventory(menuHolder, 54, "§8§l背包仓库 " + "第"+menuHolder.index+"页");
        Map<Integer, ItemStack> itemStackBk = getItemStackBk();
        for (Integer integer : itemStackBk.keySet()) {
            inventory.setItem(integer, itemStackBk.get(integer));
        }
        List<ItemStack> itemStacks = menuHolder.getItemStackByIndex();

        for (ItemStack itemStack : itemStacks) {
            inventory.setItem(inventory.firstEmpty(), itemStack);
        }

        if (menuHolder.index == 1) {
            ItemStack pageItemStack = getPageItemStack(InventoryItemStack.RedLastItem);
            inventory.setItem(47, pageItemStack);
            if (inventory.firstEmpty() == -1) {
                ItemStack pageItemStack1 = getPageItemStack(InventoryItemStack.GreenNextItem);
                inventory.setItem(51, pageItemStack1);
            } else {
                ItemStack pageItemStack1 = getPageItemStack(InventoryItemStack.RedNextItem);
                inventory.setItem(51, pageItemStack1);
            }
        } else {
            if (inventory.firstEmpty() == -1) {
                ItemStack pageItemStack1 = getPageItemStack(InventoryItemStack.GreenNextItem);
                inventory.setItem(51, pageItemStack1);
                ItemStack pageItemStack = getPageItemStack(InventoryItemStack.GreenLastItem);
                inventory.setItem(47, pageItemStack);
            } else {
                ItemStack pageItemStack = getPageItemStack(InventoryItemStack.GreenLastItem);
                inventory.setItem(47, pageItemStack);
                ItemStack pageItemStack1 = getPageItemStack(InventoryItemStack.RedNextItem);
                inventory.setItem(51, pageItemStack1);
            }

        }

        player.openInventory(inventory);
    }


    private Map<Integer, ItemStack> getItemStackBk() {
        Map<Integer, ItemStack> map = new HashMap<>();
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE);
        itemStack.setDurability((short) 1);
        ItemStack itemStack1 = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
        itemMeta.setDisplayName("-");
        itemStack.setItemMeta(itemMeta);
        itemStack1.setItemMeta(itemMeta);
        for (int i = 0; i < 54; i++) {
            if (i < 9) {
                map.put(i, itemStack);
            } else if (i > 44) {
                map.put(i, itemStack);

            }
            int[] array = {9, 17, 18, 26, 27, 35, 36, 44};
            for (int i1 : array) {
                if (i == i1) {
                    map.put(i, itemStack1);
                }
            }
        }
        return map;
    }
}
