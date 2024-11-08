package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import store.common.constants.MessageConstants;
import store.domain.dto.ProductDto;

public class InputView {

    public static void askPurchaseProduct() {
        System.out.println(MessageConstants.PRODUCT_PURCHASE_MESSAGE);
        Console.readLine();
    }

    public void tellPromotionNotApplicable() {
        System.out.println(MessageConstants.PROMOTION_NOT_APPLY_MESSAGE);
        Console.readLine();
    }

    public void tellFreeProductProvide() {
        System.out.println(MessageConstants.PROMOTION_PROVIDE_FREE_PRODUCT_MESSAGE);
        Console.readLine();
    }

    public void askMembershipDiscount() {
        System.out.println(MessageConstants.MEMBERSHIP_DISCOUNT_MESSAGE);
        Console.readLine();
    }

    public void buyAnotherProduct() {
        System.out.println(MessageConstants.EXIT_MESSAGE);
        Console.readLine();
    }
}
