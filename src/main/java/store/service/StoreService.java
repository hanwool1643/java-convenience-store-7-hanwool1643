package store.service;

import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;

public interface StoreService {
    Receipt buy(String name, Long quantity, List<Product> inventory, List<Promotion> promotions);

    Long[] calculateTotalReceipts(List<Receipt> receipts);

    Long calculateMembershipDiscount(Long price, String answer);
}
