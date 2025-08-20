package pl.cezarysanecki.unittestingrevisitedworkshop.calculator;

public final class ExternalPriceCalculatorWrapper implements ExternalPriceCalculatorInterface {

    @Override
    public double computeFinalPrice(double price) {
        return ExternalPriceCalculator.computeFinalPrice(price);
    }

}
