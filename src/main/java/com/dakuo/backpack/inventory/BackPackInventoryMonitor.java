package com.dakuo.backpack.inventory;

import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.database.ApiImpl;
import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.service.InventoryService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class BackPackInventoryMonitor implements Listener {
    private List<Integer> slotList = Arrays.asList(45,46,47,48,49,50,51,52,53);
    @EventHandler
    public void BackPackInventory(InventoryClickEvent event){

        InventoryHolder holder = event.getInventory().getHolder();
        boolean a = false;
        if (holder instanceof BackPackInventoryHolder) {
            a = true;
        }
        if(!a){
            return;
        }

        if (event.getRawSlot() < 0 || event.getRawSlot() > event.getInventory().getSize() || event.getInventory() == null) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem();
        if (slotList.contains(event.getRawSlot())) {
            event.setCancelled(true);
            if (currentItem == null) {
                return;
            }
            if (currentItem.hasItemMeta() && currentItem.getItemMeta().getDisplayName().contains("§a§l下一页")) {
                BackPackInventoryHolder holder1 = (BackPackInventoryHolder) holder;
                holder1.setIndex(holder1.index + 1);
                holder1.backPackInventory.openInventory();
            }
            if (currentItem.hasItemMeta() && currentItem.getItemMeta().getDisplayName().contains("§a§l上一页")) {
                BackPackInventoryHolder holder1 = (BackPackInventoryHolder) holder;
                holder1.setIndex(holder1.index - 1);
                holder1.backPackInventory.openInventory();
            }
        }

        if(event.getRawSlot() == 49){
            MenuInventory menuInventory = new MenuInventory((Player) event.getWhoClicked());
            menuInventory.OpenInventory();
        }
        Inventory clickedInventory = event.getClickedInventory();
        boolean isEmpty = false;
        for (int i = 0; i < 45; i++) {
            ItemStack item = clickedInventory.getItem(i);
            if(item!=null){
                isEmpty = true;
            }
        }
        if(isEmpty){
            save((BackPackInventoryHolder) holder,clickedInventory);

        }


    }

    private void save(BackPackInventoryHolder holder, Inventory inventory){
        List<ItemStack> itemStacks = holder.getItemStacks();
        List<ItemStack> itemStacks1 = holder.itemStacks;
        for (int i = 0; i < 28; i++) {
            itemStacks1.set(itemStacks1.indexOf(itemStacks.get(i)),inventory.getItem(i));
        }
        ApiImpl api = new ApiImpl();
        try {
            StringBuilder result = new StringBuilder();
            for (ItemStack itemStack : itemStacks1) {
                String s = api.singleObjectToString(itemStack);
                if(result.toString().equalsIgnoreCase("")){
                    result = new StringBuilder(s);
                }else{
                    result.append("(fenge)").append(s);
                }
            }
            InventoryService.getInstance().updateBackPackContent(holder.id,result.toString());
        }catch (Exception e){
            e.printStackTrace();
            Backpack.plugin.getLogger().warning("§c§l数据保存失败!");
        }

    }
}
