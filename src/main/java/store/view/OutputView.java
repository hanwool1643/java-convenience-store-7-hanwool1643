package store.view;

import java.util.List;
import store.common.constants.MessageConstants;
import store.domain.dto.ProductDto;

public class OutputView {
    public static void printWelcome() {
        System.out.println(MessageConstants.WELCOME_MESSAGE);
    }

    public static void printInventoryDetail(List<ProductDto> inventory) {
        System.out.println(MessageConstants.INVENTORY_STATUS_MESSAGE);
        for (ProductDto product: inventory) {
            String convertedProductInfo = InputParser.inventoryParser(
                    product.name(),
                    product.price(),
                    product.quantity(),
                    product.promotion()
            );
            System.out.println(convertedProductInfo);
        }
    }
}
