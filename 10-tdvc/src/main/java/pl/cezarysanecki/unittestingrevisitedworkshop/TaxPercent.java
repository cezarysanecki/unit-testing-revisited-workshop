package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxPercent {
    private final BigDecimal percent;

    public TaxPercent(BigDecimal percent) {
        if (percent.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tax percent must be greater than zero");
        }
        this.percent = percent;
    }

    public BigDecimal toDecimalFraction() {
        return percent.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return percent.setScale(2, RoundingMode.HALF_UP) + "%";
    }
}
