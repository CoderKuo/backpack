package com.dakuo.backpack.service;

import com.alibaba.fastjson.JSONObject;
import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.database.SqlBase;
import com.dakuo.backpack.entity.BackPackEntity;
import com.dakuo.backpack.entity.BackPackYamlEntity;
import com.dakuo.backpack.utils.yamlDao;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    SqlBase sqlBase;
    private static InventoryService instance = new InventoryService();

    private InventoryService(){
        this.sqlBase = Backpack.sqlBase;
    }
    public static InventoryService getInstance(){
        return instance;
    }

//    public List<ItemStack> showBackPackContent(String backpack,String uuid,int index){
//
//    }

    public List<ItemStack> showBackItemStacks(List<BackPackEntity> BackPackList){
        List<ItemStack> itemStacks = new ArrayList<>();
        Integer serverVersion = Backpack.serverVersion;
        for (BackPackEntity backPackEntity : BackPackList) {
            BackPackYamlEntity backPackYamlEntity = yamlDao.BackPackYamlMap.get(backPackEntity.backpackType);
            JSONObject material = backPackYamlEntity.getMaterial();
            ItemStack itemStack = new ItemStack(Material.CHEST);
            if(material != null){
                JSONObject material1 = material.getJSONObject("Material");
                if(serverVersion > 12){
                    String name = material1.getString("name");
                    Boolean enchantment = material.getBoolean("enchantment");
                    itemStack = getItemStack(name, enchantment);
                }else{
                    Integer id = material1.getInteger("id");
                    Boolean enchantment = material.getBoolean("enchantment");
                    itemStack = getItemStack(id,enchantment);
                }
            }
            ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
            itemMeta.setDisplayName(backPackYamlEntity.getName());
            List<String> lore = backPackYamlEntity.getDescription();
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            itemStacks.add(itemStack);
        }
        return itemStacks;
    }

    public List<BackPackEntity> getBackPackListByPlayerUUID(String UUID){
        String[] idArray = getBackPackIdArrayByPlayerUUID(UUID);
        if(idArray == null){
            return null;
        }

        List<BackPackEntity> backPackEntityList = new ArrayList<>();
        Connection connection = sqlBase.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        for (String s : idArray) {
            BufferStatement bufferStatement = new BufferStatement("select * from `backpack_data` where id = ?", s);
            try {
                ps = bufferStatement.preparedStatement(connection);
                rs = ps.executeQuery();
                if(rs.next()){
                    BackPackEntity backPackEntity = new BackPackEntity(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
                    backPackEntityList.add(backPackEntity);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        sqlBase.close(rs,ps,connection);
        return backPackEntityList;
    }

    public String[] getBackPackIdArrayByPlayerUUID(String UUID){
        BufferStatement bufferStatement = new BufferStatement("select `backpacks` from `backpack_player_data` where player_uuid = ?", UUID);
        PreparedStatement ps = null;
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = sqlBase.getConnection();
            ps = bufferStatement.preparedStatement(connection);
            rs = ps.executeQuery();
            if(rs.next()){
                String string = rs.getString(1);
                return string.split(",");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ItemStack getItemStack(Object MaterialName,boolean enchantment){
        Integer serverVersion = Backpack.serverVersion;
        ItemStack itemStack = new ItemStack(Material.CHEST);
        if(serverVersion > 12){
            try {
                Class<?> aClass = Class.forName("org.bukkit.Material");
                Method getMaterial = aClass.getMethod("getMaterial", String.class, boolean.class);
                Object Material = getMaterial.invoke(aClass, MaterialName, false);
                itemStack = new ItemStack((Material) Material);
            } catch (Exception e) {
                Backpack.plugin.getLogger().warning("§c§l>>> 奖励物品材质加载失败！");
                e.printStackTrace();
            }
        }else{
            itemStack = new ItemStack((Integer) MaterialName);
        }
        if(enchantment){
            itemStack.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        }
        return itemStack;
    }



}
