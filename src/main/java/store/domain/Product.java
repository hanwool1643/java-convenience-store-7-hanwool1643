package store.domain;

import static store.common.constants.NumberConstants.PRODUCT_NAME_INDEX;
import static store.common.constants.NumberConstants.PRODUCT_PRICE_INDEX;
import static store.common.constants.NumberConstants.PRODUCT_PROMOTION_INDEX;
import static store.common.constants.NumberConstants.PRODUCT_QUANTITY_INDEX;

import store.common.constants.ErrorConstants;

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

    public void buy(Long quantity) {
        if (quantity > this.quantity) {
            throw new IllegalArgumentException(ErrorConstants.INVENTORY_SHORT_ERROR_MESSAGE);
        }
        this.quantity -= quantity;
    }

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getName() {
        return name;
    }
}
