package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import store.common.constants.ErrorConstants;
import store.common.constants.MessageConstants;
import store.common.constants.StringConstants;
import store.common.validation.InputValidation;

public class InputView {

    public static Map<String, Long> askPurchaseProduct() {
        System.out.println(MessageConstants.PRODUCT_PURCHASE_MESSAGE);
        while (true) {
            try {
                String input = Console.readLine();
                String[] inputSplit = InputValidation.validatePurchaseInput(input);
                System.out.println();
                return InputParser.purchaseInputParser(inputSplit);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String tellPromotionNotApplicable(String name, Long quantity) {
        System.out.printf(MessageConstants.PROMOTION_NOT_APPLY_MESSAGE, name, quantity);
        System.out.println();
        return getYesOrNoAnswer();
    }

    private static String getYesOrNoAnswer() {
        while(true) {
            try {
                String answer = Console.readLine();
                System.out.println();
                if (!(answer.equals(StringConstants.NO) || answer.equals(StringConstants.YES))) {
                    throw new IllegalArgumentException(ErrorConstants.INPUT_FORMAT_ERROR_MESSAGE);
                }
                return answer;
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String tellFreeProductProvide(String name, Long quantity) {
        System.out.printf(MessageConstants.PROMOTION_PROVIDE_FREE_PRODUCT_MESSAGE, name, quantity);
        System.out.println();
        return getYesOrNoAnswer();
    }

    public static String askMembershipDiscount() {
        System.out.println(MessageConstants.MEMBERSHIP_DISCOUNT_MESSAGE);
        return getYesOrNoAnswer();
    }

    public static String buyAnotherProduct() {
        System.out.println(MessageConstants.EXIT_MESSAGE);
        return getYesOrNoAnswer();
    }
}
