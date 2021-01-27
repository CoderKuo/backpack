package com.dakuo.backpack.command;

import com.dakuo.backpack.entity.BackPackEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackPackGiveCommand {
    CommandHandler commandHandler;
    private static BackPackGiveCommand instance = new BackPackGiveCommand();

    private BackPackGiveCommand(){
        commandHandler = CommandHandler.getInstance();
    }

    public static BackPackGiveCommand getInstance(){
        return instance;
    }

    public void execute(CommandSender sender,String[] args){

        if(args.length == 1 || args.length == 2){
            commandHandler.sendCommandHelpMessage(sender,"give","缺少参数");
            return;
        }
        if(args.length == 3){
            Player player = Bukkit.getPlayer(args[1]);
            try {
                int i = commandHandler.backPackService.givePlayerBackPack(player.getUniqueId().toString(), new BackPackEntity(args[2], 0));
                if(i!=-1){
                    sender.sendMessage("§a§l成功给玩家"+player.getName()+"添加一个等级为0的"+args[2]+"背包!");
                }else{
                    sender.sendMessage("§a§l未知错误");
                }
            }catch (Exception e){
                sender.sendMessage("§a§l数据库配置错误,请检查数据库配置,或根据控制台输出联系作者!");
                e.printStackTrace();
            }
            return;
        }
        if(args.length == 4){
            Player player = Bukkit.getPlayer(args[1]);
            try {
                int i = commandHandler.backPackService.givePlayerBackPack(player.getUniqueId().toString(), new BackPackEntity(args[2], Integer.parseInt(args[3])));
                if(i!=-1){
                    sender.sendMessage("§a§l成功给玩家"+player.getName()+"添加一个等级为"+args[3]+"的"+args[2]+"背包!");
                }else{
                    sender.sendMessage("§a§l未知错误");
                }
            }catch (Exception e){
                sender.sendMessage("§a§l数据库配置错误,请检查数据库配置,或根据控制台输出联系作者!");
                e.printStackTrace();
            }
            return;
        }

    }
}
