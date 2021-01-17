package com.dakuo.backpack.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class BufferStatement {
    private Object[] values;
    private String query;
    private Exception stacktrace;

    public BufferStatement(String query,Object... values){
        this.query = query;
        this.values = values;
        this.stacktrace = new Exception();
        this.stacktrace.fillInStackTrace();
    }

    public PreparedStatement preparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(this.query);

        for(int i = 1; i <= this.values.length; ++i) {
            ps.setObject(i, this.values[i - 1]);
        }

        return ps;
    }

    public StackTraceElement[] getStackTrace() {
        return this.stacktrace.getStackTrace();
    }

    public String toString() {
        return "Query: " + this.query + ", values: " + Arrays.toString(this.values);
    }
}
