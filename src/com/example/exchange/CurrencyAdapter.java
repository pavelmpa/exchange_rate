package com.example.exchange;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Currency adapter prepare data to display at ListView. Displayed rate with specified format and
 * rate delta with appropriate color to that changes.
 *
 * @author Paul
 * Date: 14.12.12
 */
public class CurrencyAdapter extends BaseAdapter {

    private static final String FORMAT = "0.0000";

    private Context context;
    private LayoutInflater inflater;
    private List<Currency> currencyList;
    private DecimalFormat formatter;

    public CurrencyAdapter(Context context, List<Currency> currencyList) {
        this.context = context;
        this.currencyList = currencyList;
        inflater = LayoutInflater.from(context);
        formatter = new DecimalFormat(FORMAT);
    }

    private class ViewHolder {
        private TextView tag;
        private TextView buy;
        private TextView buyDelta;
        private TextView sale;
        private TextView saleDelta;
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int i) {
        return currencyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return currencyList.indexOf(currencyList.get(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.currency_item, viewGroup, false);

            holder = new ViewHolder();
            holder.tag = (TextView) view.findViewById(R.id.currency_tag);
            holder.buy = (TextView) view.findViewById(R.id.buy);
            holder.buyDelta = (TextView) view.findViewById(R.id.buy_delta);
            holder.sale = (TextView) view.findViewById(R.id.sale);
            holder.saleDelta = (TextView) view.findViewById(R.id.sale_delta);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Currency currency = currencyList.get(i);

        holder.tag.setText(currency.getCurrencyTag());
        holder.buy.setText(formatter.format(currency.getBuy()));
        holder.sale.setText(formatter.format(currency.getSale()));
        setDelta(holder.buyDelta, currency.getBueDelta());
        setDelta(holder.saleDelta, currency.getSaleDelta());

        return view;
    }

    private void setDelta(TextView tv, double delta) {
        if (delta > 0) {
            tv.setTextColor(context.getResources().getColor(R.color.delta_up));
            tv.setText("+" + formatter.format(delta));
            return;
        }
        if (delta < 0) {
            tv.setTextColor(context.getResources().getColor(R.color.delta_down));
        }
        if (delta == 0) {
            tv.setTextColor(context.getResources().getColor(R.color.delta_no_change));
            tv.setText("-");
            return;
        }
        tv.setText(formatter.format(delta));
    }
}
