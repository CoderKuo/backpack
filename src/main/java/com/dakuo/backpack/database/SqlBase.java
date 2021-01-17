package com.dakuo.backpack.database;

import java.sql.Connection;

public interface SqlBase {
    Connection getConnection();

    void queue(BufferStatement bf);

    void flush();

    void close();

}
