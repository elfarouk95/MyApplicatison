//package com.app.myapplication;
//
//import android.os.Looper;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.smartarmenia.dotnetcoresignalrclientjava.HubConnection;
//import com.smartarmenia.dotnetcoresignalrclientjava.HubConnectionListener;
//import com.smartarmenia.dotnetcoresignalrclientjava.HubEventListener;
//import com.smartarmenia.dotnetcoresignalrclientjava.HubMessage;
//import com.smartarmenia.dotnetcoresignalrclientjava.WebSocketHubConnectionP2;
//
//public class Main2Activity extends AppCompatActivity implements HubConnectionListener, HubEventListener {
//
//    HubConnection connection;
//    public static String[] s = {"3a742e5b-11d3-49af-908b-c49e03f487bc", "f9f4c6a0-46b2-46c9-84e7-af667e798160"};
//    public static String senderid = s[0];
//    public static String Reciveid = s[1];
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        connection = new WebSocketHubConnectionP2("http://alfarouk96-002-site2.ftempurl.com/rtcHub", "");
//
//        connection.addListener(this);
//        connection.subscribeToEvent("ReceiveMessage", this);
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        connection.connect();
//    }
//
//    @Override
//    public void onConnected() {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(Main2Activity.this, "Connected", Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }
//
//    @Override
//    public void onDisconnected() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(Main2Activity.this, "onDisconnected", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    public void onMessage(HubMessage message) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(Main2Activity.this, message.getTarget(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void c(View view) {
//        Log.d("Main2Activity", "click");
//        try {
//            String test = "   connection.connect();\n";
//            connection.invoke("SendMessage",
//                    Reciveid,
//                    senderid,
//                    test);
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onError(Exception exception) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(Main2Activity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//    @Override
//    public void onEventMessage(HubMessage message) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(Main2Activity.this, message.getTarget(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void t(View view) {
//        connection.invoke("connect", senderid);
//       // connection.invoke("connect", Reciveid);
//    }
//}
