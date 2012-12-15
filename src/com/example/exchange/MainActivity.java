package com.example.exchange;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getCanonicalName();

    private static ListView listView;

    private Button.OnClickListener loadData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DownloadXmlTask task = new DownloadXmlTask(getApplicationContext());
            String url = "http://cashexchange.com.ua/XmlApi.ashx";
            task.execute(url);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.load_data).setOnClickListener(loadData);
        listView = (ListView) findViewById(R.id.currency_list);
    }

    public static void setAdapter(ListAdapter adapter) {
        Log.d(TAG, "List view is null: " + (listView == null));
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }
}
