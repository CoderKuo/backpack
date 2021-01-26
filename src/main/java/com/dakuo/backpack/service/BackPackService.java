package com.dakuo.backpack.service;

import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.database.SqlBase;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BackPackService {

    SqlBase sqlBase;
    public BackPackService(){
        this.sqlBase = Backpack.sqlBase;
    }


    /**
     * 查询玩家背包数据
     * @param playUUID 玩家UUID
     * @return 背包数据
     */
    public String queryBackPackDataByPlayerUUID(String playUUID){
        BufferStatement bufferStatement = new BufferStatement("select `backpacks` from backpack_player_data where player_uuid = ?", playUUID);

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
        return BackPackData + "," + BackPackId;
    }


}
