package com.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Price {

    private static final Double VAT_PERCENTAGE = 0.2;
    private Double gross;
    private Double vat;

    public Price(Double gross) {
        this.gross = gross;
        this.vat =new BigDecimal(gross * VAT_PERCENTAGE ).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }
}
