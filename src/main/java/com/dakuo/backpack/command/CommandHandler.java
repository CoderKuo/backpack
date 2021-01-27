package com.dakuo.backpack.command;

import com.dakuo.backpack.inventory.menuInventory;
import com.dakuo.backpack.service.BackPackService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    BackPackService backPackService;

    private static CommandHandler instance = new CommandHandler();
    private CommandHandler(){
        this.backPackService = new BackPackService();
    }

    public static CommandHandler getInstance(){
        return instance;
    }

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
                    BackPackGiveCommand.getInstance().execute(sender,args);
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
