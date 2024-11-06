package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.constants.MessageConstants;

public class InputView {

    public static void startMessage() {
        System.out.println(MessageConstants.WELCOME_MESSAGE);
    }

    public static void printInventoryDetail() {
        System.out.println(MessageConstants.INVENTORY_STATUS_MESSAGE);
        //TODO: 재고 내역 받아와서 내려주기
        //System.out.println(inventoryDetail);
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
