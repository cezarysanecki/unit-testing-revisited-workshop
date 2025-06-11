package pl.cezarysanecki.unittestingrevisitedworkshop._1;

public final class ExternalPriceCalculator {

    public static double computeFinalPrice(double price) {
        try {
            // Simulating a call to an external service that takes time
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }

        return price * 1.23;
    }

}
