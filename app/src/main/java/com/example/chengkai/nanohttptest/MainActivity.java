package com.example.chengkai.nanohttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.chengkai.nanohttptest.WebServer.MyServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyServer myServer = new MyServer(8080);
        try {
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
