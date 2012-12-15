package com.example.exchange;

/**
 * Class describe currency.
 *
 * @author Paul
 * Date: 14.12.12
 */
public class Currency {

    private String currencyTag;
    private double buy;
    private double bueDelta;
    private double sale;
    private double saleDelta;

    public Currency() {
        this(null, 0, 0, 0, 0);
    }

    public Currency(String currencyTag, double buy, double bueDelta, double sale, double saleDelta) {
        this.currencyTag = currencyTag;
        this.buy = buy;
        this.bueDelta = bueDelta;
        this.sale = sale;
        this.saleDelta = saleDelta;
    }

    public String getCurrencyTag() {
        return currencyTag;
    }

    public void setCurrencyTag(String currencyTag) {
        this.currencyTag = currencyTag;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getBueDelta() {
        return bueDelta;
    }

    public void setBueDelta(double bueDelta) {
        this.bueDelta = bueDelta;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public double getSaleDelta() {
        return saleDelta;
    }

    public void setSaleDelta(double saleDelta) {
        this.saleDelta = saleDelta;
    }
}
