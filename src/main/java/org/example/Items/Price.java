package org.example.Items;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Price {
    private double beforeDiscount;
    private double afterDiscount;
    private String currency;

    public Price(double beforeDiscount, double afterDiscount, String currency) {
        setBeforeDiscount(beforeDiscount);
        setAfterDiscount(afterDiscount);
        setCurrency(currency);
    }

    public Price(String beforeDiscountPriceMessage, String afterDiscountPriceMessage) {
        setBeforeDiscount(getPriceFromPriceMessage(beforeDiscountPriceMessage));
        setAfterDiscount(getPriceFromPriceMessage(afterDiscountPriceMessage));
        setCurrency(getCurrencyFromPriceMessage(beforeDiscountPriceMessage));

    }

    public Price(String beforeDiscountPriceMessage) {
        setBeforeDiscount(getPriceFromPriceMessage(beforeDiscountPriceMessage));
        setCurrency(getCurrencyFromPriceMessage(beforeDiscountPriceMessage));
    }

    public Price() {

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
    public void setAfterDiscount(String afterDiscount) {
        this.afterDiscount = getPriceFromPriceMessage(afterDiscount);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public static String getCurrencyFromPriceMessage(String priceMessage) {
        return String.valueOf(priceMessage.charAt(priceMessage.length()-1));
    }

    public static double getPriceFromPriceMessage(String priceMessage) {
        priceMessage = getDigitsFromString(priceMessage);
        priceMessage = priceMessage.replace(',','.');
        return Double.parseDouble(priceMessage);
    }

    private static String getDigitsFromString(String sentence) {
        StringBuilder digits = new StringBuilder();
        for (int letterIndex = 0; letterIndex < sentence.length(); letterIndex++) {
            if (sentence.charAt(letterIndex) >= '0' && sentence.charAt(letterIndex) <= '9' || sentence.charAt(letterIndex) == ',') {
                digits.append(sentence.charAt(letterIndex));
            }
        }
        return digits.toString();
    }



}
