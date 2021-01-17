package com.dakuo.backpack.command;

import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.inventory.menuInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("bp") && sender instanceof Player && args.length == 0){
            menuInventory menuInventory = new menuInventory();
            menuInventory.OpenInventory((Player) sender);
            return true;
        }

        if(label.equalsIgnoreCase("bp") ){
            switch (args[0]){
                case "give":
                    if(args.length == 1 || args.length == 2){
                        sendCommandHelpMessage(sender,"give","缺少参数");
                        break;
                    }
                    if(args.length == 3){
                        Player player = Bukkit.getPlayer(args[1]);
                        Backpack.sqlBase.queue(new BufferStatement("insert into backpack_data(id,player_uuid,level) values (?,?,?)",args[2],player.getUniqueId(),0));
                        sender.sendMessage("§a§l成功给玩家"+player.getName()+"添加一个等级为0的"+args[2]+"背包!");
                    }
                    if(args.length == 4){
                        Player player = Bukkit.getPlayer(args[1]);
                        Backpack.sqlBase.queue(new BufferStatement("insert into backpack_data(id,player_uuid,level) values (?,?,?)",args[2],player.getUniqueId(),args[3]));
                        sender.sendMessage("§a§l成功给玩家"+player.getName()+"添加一个等级为"+args[3]+"的"+args[2]+"背包!");
                    }
            }
        }


        return true;
    }

    public void sendCommandHelpMessage(CommandSender sender,String command,String message){
        sender.sendMessage("§c§l命令输入错误！");
        sender.sendMessage("§c§l错误提示："+message);
        sender.sendMessage("§c§l该命令的正确格式为"+commandMap(command));
    }

    public String commandMap(String command){
        if(command.equalsIgnoreCase("give")){
            return "/bq give [玩家名] [背包ID] [等级(可选,默认为0)]";
        }
        return "";
    }
}
