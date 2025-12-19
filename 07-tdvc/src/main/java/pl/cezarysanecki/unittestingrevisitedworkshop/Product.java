package pl.cezarysanecki.unittestingrevisitedworkshop;

public class Product {
    private final String id;
    private boolean available;
    private double price;

    public Product(String id) {
        this.id = id;
        if (id.startsWith("catalogue_")) {
            this.available = false;
        } else {
            this.available = true;
        }
        this.price = 0.0d;
    }

    public void buy() {
        this.available = false;
    }

    public boolean isAvailable() {
        return available;
    }

    public void changePrice(double price) {
        if (!available) {
            throw new IllegalStateException("Cannot change price of a bought or unavailable product");
        }
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}