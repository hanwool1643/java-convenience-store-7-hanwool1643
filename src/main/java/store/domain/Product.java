package store.domain;

public class Product {
    private final String name;
    private final Long price;
    private Long quantity;
    private final String promotion;

    public Product(String name, Long price, Long quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getName() {
        return name;
    }
}
