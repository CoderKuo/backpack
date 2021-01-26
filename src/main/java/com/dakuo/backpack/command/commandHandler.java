package com.dakuo.backpack.command;

import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.inventory.menuInventory;
import com.dakuo.backpack.service.BackPackService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;

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
                        try {


                            BufferStatement bufferStatement = new BufferStatement("insert into backpack_data(backpackType,level) values (?,?)", args[0], 0);
                            ResultSet generatedKeys = bufferStatement.preparedStatement(Backpack.sqlBase.getConnection()).getGeneratedKeys();
                            Integer key = null;
                            if(generatedKeys.next()){
                                key = generatedKeys.getInt(1);
                            }
                            BackPackService backPackService = new BackPackService();
                            String backPackData = backPackService.queryBackPackDataByPlayerUUID(player.getUniqueId().toString());
                            String newBackPackData = BackPackService.addBackPackToOldBackPackData(backPackData, key);
                            Backpack.sqlBase.queue(new BufferStatement("insert into backpack_player_data(player_uuid,backpacks) values (?,?)",player.getUniqueId().toString(),newBackPackData));

                            sender.sendMessage("§a§l成功给玩家"+player.getName()+"添加一个等级为0的"+args[2]+"背包!");
                        }catch (Exception e){
                            sender.sendMessage("§a§l数据库配置错误,请检查数据库配置,或根据控制台输出联系作者!");
                            e.printStackTrace();
                        }
                        break;
                    }
                    if(args.length == 4){
                        Player player = Bukkit.getPlayer(args[1]);
                        try {
                            Backpack.sqlBase.queue(new BufferStatement("insert into backpack_data(id,player_uuid,level) values (?,?,?)",args[2],player.getUniqueId(),args[3]));
                            sender.sendMessage("§a§l成功给玩家"+player.getName()+"添加一个等级为"+args[3]+"的"+args[2]+"背包!");
                        }catch (Exception e){
                            sender.sendMessage("§a§l数据库配置错误,请检查数据库配置,或根据控制台输出联系作者!");
                            e.printStackTrace();
                        }
                        break;
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
