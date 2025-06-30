package pl.cezarysanecki.unittestingrevisitedworkshop;

import lombok.Getter;

@Getter
public class Product {
    private final String id;
    private boolean available;
    private double price;

    public Product(String id) {
        this.id = id;
        this.available = !id.startsWith("catalogue_");
        this.price = 0.0;
    }

    public void buy() {
        this.available = false;
    }

    public void changePrice(double price) {
        if (!available) {
            throw new IllegalStateException("Cannot change price of a bought product");
        }
        this.price = price;
    }
}
