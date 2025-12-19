package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxPercent2 {
    private final BigDecimal percent;

    public TaxPercent2(BigDecimal percent) {
        if (percent.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tax percent must be greater than zero");
        }
        this.percent = percent;
    }

    public BigDecimal toDecimalFraction() {
        return percent.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        BigDecimal rounded = percent.setScale(2, RoundingMode.HALF_UP);
        return rounded.toPlainString() + "%";
    }
}