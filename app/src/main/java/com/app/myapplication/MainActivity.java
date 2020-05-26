package com.app.myapplication;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.signalr.Action2;
import com.microsoft.signalr.Action3;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

import static com.app.myapplication.MainActivity.Reciveid;
import static com.app.myapplication.MainActivity.senderid;
import static com.app.myapplication.MainActivity.ss;


public class MainActivity extends AppCompatActivity {
    HubConnection hubConnection;

    EditText etMessageText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMessageText = findViewById(R.id.etMessageText);
        load();


        //  load();
     /*   String input = "http://alfarouk96-002-site2.ftempurl.com/rtcHub";

        hubConnection = HubConnectionBuilder.create(input).build();

        TextView textView = findViewById(R.id.tvMain);
        ListView listView = (ListView) findViewById(R.id.lvMessages);
        Button sendButton = (Button) findViewById(R.id.bSend);


        List<String> messageList = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(arrayAdapter);


        hubConnection.on("ReceiveMessage", new Action3<String, String, String>() {
            @Override
            public void invoke(String senderId, String name, String message) {
                arrayAdapter.add(name + " :  " + message);
                arrayAdapter.notifyDataSetChanged();
            }
        }, String.class, String.class, String.class);


        new HubConnectionTask().execute(hubConnection);*/

    }

    public void run(View view) {
        if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            hubConnection.send("SendMessage",
                    "f9f4c6a0-46b2-46c9-84e7-af667e798160",
                    "3a742e5b-11d3-49af-908b-c49e03f487bc",
                    "Tt");

        }

    }

    public static Completable ss;
    public static String[] s = {"3a742e5b-11d3-49af-908b-c49e03f487bc", "f9f4c6a0-46b2-46c9-84e7-af667e798160"};
    public static String senderid = s[0];
    public static String Reciveid = s[1];


    private void load() {

        hubConnection = HubConnectionBuilder.create("http://alfarouk96-002-site2.ftempurl.com/rtcHub").build();

        TextView textView = findViewById(R.id.tvMain);
        ListView listView = (ListView) findViewById(R.id.lvMessages);
        Button sendButton = (Button) findViewById(R.id.bSend);
        EditText editText = (EditText) findViewById(R.id.etMessageText);

        List<String> messageList = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(arrayAdapter);


        Handler h = new Handler(Looper.myLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                hubConnection.on("ReceiveMessage", new Action3<String, String, String>() {
                    @Override
                    public void invoke(String senderId, String name, String message) {
                        arrayAdapter.add(name + " :  " + message);
                        arrayAdapter.notifyDataSetChanged();
                        new HubConnectionTask().execute(hubConnection);
                    }
                }, String.class, String.class, String.class);
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                arrayAdapter.add(message);
                arrayAdapter.notifyDataSetChanged();
                editText.setText("");
                try {
                    if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                        hubConnection.send("SendMessage",
                                senderid,
                                Reciveid,
                                message);

                    } else {
                        new HubConnectionTask().execute(hubConnection);
                        hubConnection.send("SendMessage",
                                senderid,
                                Reciveid,
                                s);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        //  hubConnection.send("connect", "f9f4c6a0-46b2-46c9-84e7-af667e798160", true);
        //   hubConnection.send("connect", "3a742e5b-11d3-49af-908b-c49e03f487bc", true);

    }


}

class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(HubConnection... hubConnections) {
        HubConnection hubConnection = hubConnections[0];


        if (hubConnection.getConnectionState() == HubConnectionState.DISCONNECTED) {
            hubConnection.setKeepAliveInterval(15000 * 15000);
            ss = hubConnection.start();
            ss.blockingAwait();
            hubConnection.send("connect", senderid, true);
        }

        return null;
    }
}

