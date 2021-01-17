package com.dakuo.backpack.database;

import java.io.IOException;

public interface Api {
    byte[] singleObjectToByteArray(Object object) throws IOException;

    <T> T singleObjectFromString(String serialized, Class<T> classOfT) throws IOException;

    @SuppressWarnings("unchecked")
    <T> T singleObjectFromByteArray(byte[] serialized, Class<T> classOfT) throws IOException;

    String singleObjectToString(Object object) throws IOException;
}
