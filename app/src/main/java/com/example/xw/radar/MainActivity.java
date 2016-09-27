package com.example.xw.radar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private MyRadar mRadar;
    double[] data={100,100,100,100,50,100,20};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadar= (MyRadar) findViewById(R.id.radar);

        mRadar.setData(data);

    }
}
