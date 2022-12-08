package com.example.aidlclientapp1;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidlclientapp.AIDLClient;

public class MyAIDLClientService extends Service {
    public MyAIDLClientService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;

    }


    private final AIDLClient.Stub binder = new AIDLClient.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getPID() throws RemoteException {
            return Process.myPid();
        }

        @Override
        public String setDisplayData(String packageName, int clientPID, String messageFromServer) throws RemoteException {
            SharedPreferences sp = getSharedPreferences("AIDL_Client", getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            String txt = "last Message \n" + "ServerPackage Name : " + packageName + "\n" + "Server PID : " + clientPID + "\n Message From Server : " + messageFromServer;

            ed.putString("AIDL_Server_Client", txt);
            ed.apply();

            Log.d("myserver", txt);

            String sendMessage = "";
            sendMessage += "Package Name : " + getPackageName() + "\n";
            sendMessage += "Package PID : " + getPID() + "\n";
            sendMessage += "Message Received" + "\n";


            return sendMessage;
        }
    };


}