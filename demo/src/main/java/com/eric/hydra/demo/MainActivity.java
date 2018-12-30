package com.eric.hydra.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eric.hydra.connect.Session;
import com.eric.hydra.constant.Error;
import com.eric.hydra.scan.Callback;
import com.eric.hydra.scan.Request;
import com.eric.hydra.scan.Response;
import com.eric.hydra.HydraClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private HydraClient mHydraClient = new HydraClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.hello_world);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = Request.Builder()
                        .timeout(1000)
                        .build();
                mHydraClient.scan(request, new Callback() {
                    @Override
                    public void onInterrupt(Error error) {
                        Log.d(TAG, error.toString());
                    }

                    @Override
                    public void onResponse(Response response) {
                        Log.d(TAG, response.toString());
                        Session session = new Session(response.getDevice());
                        session.connect(MainActivity.this);
                    }
                });
            }
        });
    }
}
