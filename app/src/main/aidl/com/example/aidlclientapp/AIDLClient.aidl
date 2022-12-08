// AIDLClient.aidl

package com.example.aidlclientapp;

// Declare any non-default types here with import statements

interface AIDLClient {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    int getPID();
    String setDisplayData(String packageName,int clientPID,String messageFromServer);
}