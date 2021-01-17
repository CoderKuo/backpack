package com.dakuo.backpack.command;

import com.dakuo.backpack.inventory.menuInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class backpack implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("openbackpack")){
            menuInventory menuInventory = new menuInventory();
            menuInventory.OpenInventory((Player) sender);
        }
        return true;
    }
}
