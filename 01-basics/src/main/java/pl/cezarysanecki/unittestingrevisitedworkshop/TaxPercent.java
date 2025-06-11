package pl.cezarysanecki.unittestingrevisitedworkshop;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public record TaxPercent(BigDecimal value) {

    private static final int ONE_HUNDRED = 100;

    public TaxPercent {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Tax should be greater than or equal to 0.");
        }
    }

    public BigDecimal toDecimalFraction() {
        return value.divide(BigDecimal.valueOf(ONE_HUNDRED), new MathContext(2, RoundingMode.HALF_UP));
    }

    @Override
    public String toString() {
        return value.setScale(2, RoundingMode.HALF_UP) + "%";
    }
}
