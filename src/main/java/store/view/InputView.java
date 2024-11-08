package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.constants.MessageConstants;
import store.common.validation.InputValidation;

public class InputView {

    public static String[] askPurchaseProduct() {
        System.out.println(MessageConstants.PRODUCT_PURCHASE_MESSAGE);
        while (true) {
            try {
                String input = Console.readLine();
                return InputValidation.validatePurchaseInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
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
