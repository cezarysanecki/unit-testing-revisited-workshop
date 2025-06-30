package pl.cezarysanecki.unittestingrevisitedworkshop._1;

public class FakeExternalPriceCalculatorWrapper implements PriceCalculator {
    @Override
    public double computeFinalPrice(double price) {
        return price * 1.23;
    }
}