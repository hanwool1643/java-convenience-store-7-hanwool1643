package store.domain;

public class Receipt {
    private final String productName;
    private Long totalQuantity;
    private Long discountQuantity;
    private Long price;

    public Receipt(String productName, Long totalQuantity, Long discountQuantity, Long price) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.discountQuantity = discountQuantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public Long getTotalPrice() {
        return price * totalQuantity;
    }

    public Long getDiscountPrice() {
        return price * discountQuantity;
    }

    public Long getDiscountQuantity() {
        return discountQuantity;
    }
}
