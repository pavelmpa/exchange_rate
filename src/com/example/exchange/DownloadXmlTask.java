package com.example.exchange;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.exchange.xml.CurrencyRateXmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Parse XML, and create adapter that set to ListView.
 *
 * @author Paul
 * Date: 14.12.12
 */
public class DownloadXmlTask extends AsyncTask<String, Void, List<Currency>> {

    private static final String TAG = DownloadXmlTask.class.getCanonicalName();

    private Context context;

    public DownloadXmlTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Currency> doInBackground(String... strings) {
        List<Currency> currencyLis = null;
        try {
            currencyLis = loadCurrencyDataFromNetwork(strings[0]);
        } catch (XmlPullParserException e) {
            Log.e(TAG, "Xml parser", e);
        } catch (IOException e) {
            Log.e(TAG, "IO exception", e);
        }
        return currencyLis;
    }

    private List<Currency> loadCurrencyDataFromNetwork(String urlString) throws IOException, XmlPullParserException {
        InputStream in = null;
        List<Currency> currencyLis = null;
        try {
            URL url = new URL(urlString);
            in = getInputStream(url);
            CurrencyRateXmlPullParser currencyRateXmlPullParser = new CurrencyRateXmlPullParser();
            currencyLis = currencyRateXmlPullParser.parse(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Url", e);
        } finally {
            if (in != null) in.close();
        }
        return currencyLis;
    }

    private InputStream getInputStream(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        return connection.getInputStream();
    }

    @Override
    protected void onPostExecute(List<Currency> currencyList) {
        super.onPostExecute(currencyList);
        CurrencyAdapter adapter = new CurrencyAdapter(context, currencyList);
        MainActivity.setAdapter(adapter);
    }
}
