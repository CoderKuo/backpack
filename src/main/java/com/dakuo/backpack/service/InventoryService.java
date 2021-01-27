package com.dakuo.backpack.service;

import com.dakuo.backpack.Backpack;
import com.dakuo.backpack.database.BufferStatement;
import com.dakuo.backpack.database.SqlBase;
import com.dakuo.backpack.entity.BackPackEntity;

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


}
