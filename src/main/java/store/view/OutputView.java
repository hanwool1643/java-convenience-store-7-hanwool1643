package store.view;

import java.util.List;
import store.common.constants.MessageConstants;
import store.domain.Product;

public class OutputView {
    public static void printWelcome() {
        System.out.println(MessageConstants.WELCOME_MESSAGE);
    }

    public static void printInventoryDetail(List<Product> inventory) {
        System.out.println(MessageConstants.INVENTORY_STATUS_MESSAGE);
        for (Product product: inventory) {
            String convertedProductInfo = InputParser.inventoryParser(
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getPromotion()
            );
            System.out.println(convertedProductInfo);
        }
    }
}
