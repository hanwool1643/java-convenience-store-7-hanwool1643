package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.common.constants.MessageConstants;
import store.domain.dto.ProductDto;

public class InputView {

    public static void startMessage() {
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

    public static void askPurchaseProduct() {
        System.out.println(MessageConstants.PRODUCT_PURCHASE_MESSAGE);
        Console.readLine();
    }

    public void tellPromotionNotApplicable() {
        System.out.println(MessageConstants.PROMOTION_NOT_APPLY_MESSAGE);
    }

    public void tellFreeProductProvide() {
        System.out.println(MessageConstants.PROMOTION_PROVIDE_FREE_PRODUCT_MESSAGE);
    }

    public void askMembershipDiscount() {
        System.out.println(MessageConstants.MEMBERSHIP_DISCOUNT_MESSAGE);
    }

    public void exitMessage() {
        System.out.println(MessageConstants.EXIT_MESSAGE);
    }
}
