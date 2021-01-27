package com.dakuo.backpack.service;

import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.database.SqlBase;
import com.dakuo.backpack.entity.BackPackEntity;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BackPackService {

    SqlBase sqlBase;
    public BackPackService(){
        this.sqlBase = Backpack.sqlBase;
    }


    public int givePlayerBackPack(String playerUUID, BackPackEntity backpack){
        BufferStatement bufferStatement = new BufferStatement("insert into `backpack_data` (`backpackType`,`level`) values (?,?)", backpack.getBackpackType(), backpack.getLevel());
        int id = -1;
        int result = -1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {

            connection = sqlBase.getConnection();

            ps = bufferStatement.preparedStatement(connection);
            result = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            String playerBackPackData = queryBackPackDataByPlayerUUID(playerUUID);
            String insertedBackPackData = addBackPackToOldBackPackData(playerBackPackData, id);
            if(playerBackPackData == null){
                sqlBase.queue(new BufferStatement("insert into `backpack_player_data` values(?,?)",playerUUID,insertedBackPackData));

            }else{
                sqlBase.queue(new BufferStatement("update `backpack_player_data` set backpacks = ? where player_uuid = ?",insertedBackPackData,playerUUID));
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlBase.close(rs,ps,connection);
        }

        return -1;

    }


    /**
     * 查询玩家背包数据
     * @param playerUUID 玩家UUID
     * @return 背包数据
     */
    public String queryBackPackDataByPlayerUUID(String playerUUID){
        BufferStatement bufferStatement = new BufferStatement("select `backpacks` from backpack_player_data where player_uuid = ?", playerUUID);

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = sqlBase.getConnection();
            ps = bufferStatement.preparedStatement(connection);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlBase.close(rs,ps,connection);
        }
        return null;
    }

    /**
     * 添加背包id到背包数据中
     * @param BackPackData 背包数据
     * @param BackPackId 背包ID
     * @return 新的背包数据
     */
    public static String addBackPackToOldBackPackData(String BackPackData, Integer BackPackId){
        if(BackPackData == null){
            return String.valueOf(BackPackId);
        }else {
            return BackPackData + "," + BackPackId;
        }
    }


}
