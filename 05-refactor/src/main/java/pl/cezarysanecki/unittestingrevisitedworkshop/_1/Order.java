package pl.cezarysanecki.unittestingrevisitedworkshop._1;

import java.util.List;

public record Order(
        Long orderId,
        List<Product> products
) {

    public double totalPrice() {
        return products.stream()
                .mapToDouble(product -> product.price * product.quantity)
                .sum();
    }

    public record Product(
            Long productId,
            Long quantity,
            double price
    ) {
    }

}
