package org.example.Items;

public class Price {
    private double beforeDiscount;
    private double afterDiscount;
    private String currency;

    public Price(double beforeDiscount, double afterDiscount, String currency) {
        setBeforeDiscount(beforeDiscount);
        setAfterDiscount(afterDiscount);
        setCurrency(currency);
    }
    public Price(String beforeDiscountPriceMessage,String afterDiscountPriceMessage){
        setBeforeDiscount(getPriceFromPriceMessage(beforeDiscountPriceMessage));
        setAfterDiscount(getPriceFromPriceMessage(afterDiscountPriceMessage));
        setCurrency(getCurrencyFromPriceMessage(beforeDiscountPriceMessage));

    }
    public Price (String beforeDiscountPriceMessage)
    {
        setBeforeDiscount(getPriceFromPriceMessage(beforeDiscountPriceMessage));
        setCurrency(getCurrencyFromPriceMessage(beforeDiscountPriceMessage));
    }

    public double getBeforeDiscount() {
        return beforeDiscount;
    }

    public void setBeforeDiscount(double beforeDiscount) {
        this.beforeDiscount = beforeDiscount;
    }

    public double getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(double afterDiscount) {
        this.afterDiscount = afterDiscount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public static String getCurrencyFromPriceMessage(String priceMessage)
    {
        return priceMessage.split(" ")[1];
    }
    public static double getPriceFromPriceMessage(String priceMessage){
       return Double.parseDouble(priceMessage.split(" ")[0].replace(",","."));

    }


}
