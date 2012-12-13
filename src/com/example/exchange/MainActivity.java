package com.example.exchange;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
