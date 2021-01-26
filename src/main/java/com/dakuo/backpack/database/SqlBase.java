package com.dakuo.backpack.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface SqlBase {
    Connection getConnection();

    void queue(BufferStatement bf);

    void flush();

    void close(ResultSet rs, Statement ptmt, Connection conn);

    void close(ResultSet rs, PreparedStatement ptmt, Connection conn);


}