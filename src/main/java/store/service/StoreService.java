package store.service;

import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;

public interface StoreService {
    Receipt buy(String name, Long quantity, List<Product> inventory, List<Promotion> promotions);

    Long[] calculateTotalReceipts(List<Receipt> receipts);

    Long calculateMembershipDiscount(Long price, String answer);

    Receipt getProductFreeOrNot(Product promotionProduct, Long quantity, Long freeQuantity, String answer);

    Receipt buyInSufficientPromotionStockOrNot(Product promotionProduct, Product nonPromotionProduct,
                                               Long quantity, Long freeQuantity, String answer, Long promotionNotAppliedQuantity);

    long calculatePromotionNotAppliedQuantity(Long quantity, Long promotionProductQuantity, Promotion promotion);

    long calculateActualFreeQuantity(Long promotionProductQuantity, Promotion promotion);
}
