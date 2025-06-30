package pl.cezarysanecki.unittestingrevisitedworkshop._1;

public class ExternalPriceCalculatorWrapper implements PriceCalculator{

    @Override
    public double computeFinalPrice(double price) {
        return ExternalPriceCalculator.computeFinalPrice(price);
    }
}
