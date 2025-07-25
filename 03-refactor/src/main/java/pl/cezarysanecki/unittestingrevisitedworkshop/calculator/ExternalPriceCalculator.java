package pl.cezarysanecki.unittestingrevisitedworkshop.calculator;

public final class ExternalPriceCalculator {

    public static double computeFinalPrice(double price) {
        try {
            // Simulating a call to an external service that takes time
            Thread.sleep(10_000);
        } catch (InterruptedException ignored) {
        }

        return price * 1.23;
    }

}
