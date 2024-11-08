package store.domain;

import static store.common.constants.NumberConstants.PRODUCT_NAME_INDEX;
import static store.common.constants.NumberConstants.PRODUCT_PRICE_INDEX;
import static store.common.constants.NumberConstants.PRODUCT_PROMOTION_INDEX;
import static store.common.constants.NumberConstants.PRODUCT_QUANTITY_INDEX;

public class Product {
    private final String name;
    private final Long price;
    private Long quantity;
    private final String promotion;

    public Product(String[] productsInfo) {
        String name = productsInfo[PRODUCT_NAME_INDEX];
        long price = Long.parseLong(productsInfo[PRODUCT_PRICE_INDEX]);
        long quantity = Long.parseLong(productsInfo[PRODUCT_QUANTITY_INDEX]);
        String promotion = productsInfo[PRODUCT_PROMOTION_INDEX];

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
