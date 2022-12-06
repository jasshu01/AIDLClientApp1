package com.example.aidlserverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidlclientapp1.R;

import java.io.LineNumberReader;

public class MainActivity extends AppCompatActivity {


    AIDLServer aidlServerService = null;

    private ServiceConnection myServiceConnection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            aidlServerService = AIDLServer.Stub.asInterface(iBinder);
            Log.d("myaidlclient", "Connected to Server");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            aidlServerService = null;
            Log.d("myaidlclient", "DisConnected From Server");

        }
    };

    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("com.jasshugarg.myAIDLSERVER");
//        intent.setPackage("com.example.aidlserverapp");
//        intent.setComponent(new ComponentName("com.example.aidlserverapp","com.example.aidlserverapp.MyAIDLServerService"));
//          getApplicationContext().bindService(intent, myServiceConnection, BIND_AUTO_CREATE);
        bind(intent);


        Button button = findViewById(R.id.button);
        TextView received = findViewById(R.id.received);
        EditText sendMessage = findViewById(R.id.sendMessage);

        Log.d("myaidlclient", "Client App");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (aidlServerService == null)
                        bind(intent);
                    if (aidlServerService != null) {
                        aidlServerService.setDisplayData(getPackageName(), getTaskId(), sendMessage.getText().toString());
                        Toast.makeText(MainActivity.this, "Message Sent To Server", Toast.LENGTH_SHORT).show();
                        sendMessage.setText("");
                    }

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void bind(Intent intent) {

//        intent.setClassName("com.example.aidlserverapp", "com.example.aidlserverapp.MyAIDLServerService");

        intent.setComponent(new ComponentName("com.example.aidlserverapp", "com.example.aidlserverapp.MyAIDLServerService"));


        if (getBaseContext().getApplicationContext().bindService(intent, myServiceConnection, getApplicationContext().BIND_AUTO_CREATE)) {
            Log.v("myaidlclient", "Bind service Succeeded");
        } else {
            Log.v("myaidlclient", "Bind service failed");
        }

    }
}