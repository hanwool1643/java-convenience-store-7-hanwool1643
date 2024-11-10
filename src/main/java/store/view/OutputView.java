package store.view;

import java.util.List;
import store.common.constants.MessageConstants;
import store.domain.Product;
import store.domain.Receipt;

public class OutputView {
    public static void printWelcome() {
        System.out.println(MessageConstants.WELCOME_MESSAGE);
    }

    public static void printInventoryDetail(List<Product> inventory) {
        System.out.println(MessageConstants.INVENTORY_STATUS_MESSAGE);
        for (Product product : inventory) {
            String convertedProductInfo = InputParser.inventoryParser(
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getPromotion()
            );
            System.out.println(convertedProductInfo);
        }
        System.out.println();
    }
    //TODO: 리팩토링 필요
    public static void printFinalReceipt(List<Receipt> receipts, Long promotionDiscount, Long membershipDiscount) {
        Long sumOfTotalPrice = 0L;
        System.out.println("===============W 편의점===============");
        System.out.println("상품명             수량              금액");
        for (Receipt receipt : receipts) {
            int productNameLength = receipt.getProductName().length();
            int productQuantityLength = receipt.getTotalQuantity().toString().length();
            System.out.print(receipt.getProductName());
            System.out.print(" ".repeat(13 - productNameLength));
            System.out.print(receipt.getTotalQuantity());
            System.out.print(" ".repeat(14 - productQuantityLength));
            System.out.println(InputParser.giveCommaToPrice(receipt.getTotalPrice().toString().split("")));
            sumOfTotalPrice += receipt.getTotalPrice();
        }
        System.out.println("===============증  정================");
        List<Receipt> discountReceipts = receipts.stream().filter(receipt -> receipt.getDiscountPrice() > 0).toList();
        for (Receipt receipt : discountReceipts) {
            int productNameLength = receipt.getProductName().length();
            System.out.print(receipt.getDiscountPrice());
            System.out.print(" ".repeat(13 - productNameLength));
            System.out.println(receipt.getDiscountQuantity());
        }
        System.out.println("====================================");
        System.out.println("총구매액          " + InputParser.giveCommaToPrice(
                sumOfTotalPrice.toString().split("")));
        System.out.println("행사할인                 " + "-" + InputParser.giveCommaToPrice(promotionDiscount.toString().split("")));
        System.out.println("멤버십할인    "+ "-"+ InputParser.giveCommaToPrice(membershipDiscount.toString().split("")));
        long totalPriceToPay = sumOfTotalPrice - promotionDiscount - membershipDiscount;
        System.out.println("내실돈          " + InputParser.giveCommaToPrice(Long.toString(totalPriceToPay).split("")) );
        System.out.println();
    }
}
