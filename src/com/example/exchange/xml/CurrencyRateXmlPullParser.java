package com.example.exchange.xml;

import android.util.Log;
import android.util.Xml;
import com.example.exchange.Currency;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul
 * Date: 14.12.12
 */
public class CurrencyRateXmlPullParser {

    private static final String TAG = CurrencyRateXmlPullParser.class.getCanonicalName();

    private static final String RESULTS = "Results";
    private static final String CURRENCY_ITEM = "Element";
    private static final String CURRENCY_TAG = "Currency";
    private static final String BUY = "Buy";
    private static final String SALE = "Sale";
    private static final String BUY_DELTA = "BuyDelta";
    private static final String SALE_DELTA = "SaleDelta";

    private static final String NAME_SPACE = null;

    public List<Currency> parse(InputStream in) throws IOException, XmlPullParserException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readResults(parser);
        } finally {
            in.close();
        }
    }

    public List<Currency> readResults(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<Currency> currencyList = new ArrayList<Currency>();
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, RESULTS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String element = parser.getName();
            if (element.equals(CURRENCY_ITEM)) {
                currencyList.add(readCurrencyItem(parser));
            } else {
                skip(parser);
            }
        }
        return currencyList;
    }

    private Currency readCurrencyItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, CURRENCY_ITEM);

        String currencyTag = null;
        double buy = 0;
        double sale = 0;
        double buyDelta = 0;
        double saleDelta = 0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(CURRENCY_TAG)) {
                currencyTag = readCurrencyTag(parser);
            } else if (name.equals(BUY)) {
                buy = readBuy(parser);
            } else if (name.equals(SALE)) {
                sale = readSale(parser);
            } else if (name.equals(BUY_DELTA)) {
                buyDelta = readBuyDelta(parser);
            } else if (name.equals(SALE_DELTA)) {
                saleDelta = readSaleDelta(parser);
            } else {
                skip(parser);
            }
        }
        return new Currency(currencyTag, buy, buyDelta, sale, saleDelta);
    }

    private String readCurrencyTag(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, CURRENCY_TAG);
        String  currencyTag = readText(parser);
        parser.require(XmlPullParser.END_TAG, NAME_SPACE, CURRENCY_TAG);
        return currencyTag;
    }

    private double readBuy(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, BUY);
        String  buy = readText(parser);
        parser.require(XmlPullParser.END_TAG, NAME_SPACE, BUY);
        return Double.valueOf(buy);
    }

    private double readSale(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, SALE);
        String  sale = readText(parser);
        parser.require(XmlPullParser.END_TAG, NAME_SPACE, SALE);
        return Double.valueOf(sale);
    }

    private double readBuyDelta(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, BUY_DELTA);
        String  buyDelta = readText(parser);
        parser.require(XmlPullParser.END_TAG, NAME_SPACE, BUY_DELTA);
        return Double.valueOf(buyDelta);
    }

    private double readSaleDelta(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, SALE_DELTA);
        String  saleDelta = readText(parser);
        parser.require(XmlPullParser.END_TAG, NAME_SPACE, SALE_DELTA);
        return Double.valueOf(saleDelta);
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String text = "";
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.getText();
            parser.nextTag();
        }
        return text;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException {
        int depth = 1;
        while (depth != 0) {
            Log.d(TAG, "Skip: " + parser.getName());
            if (XmlPullParser.END_TAG == parser.getEventType()) {
                depth++;
            } else if (XmlPullParser.START_TAG == parser.getEventType()) {
                depth--;
            }
        }
    }
}
