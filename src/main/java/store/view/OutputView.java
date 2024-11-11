package store.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.common.constants.MessageConstants;
import store.common.constants.NumberConstants;
import store.common.constants.StringConstants;
import store.domain.Product;
import store.domain.Receipt;
import store.view.utils.InputParser;

public class OutputView {
    public static void printWelcome() {
        System.out.println(MessageConstants.WELCOME_MESSAGE);
    }

    public static void printInventoryDetail(List<Product> inventory) {
        printInventoryHeader();
        printInventoryProducts(inventory);
        printGroupedProducts(inventory);
        printLineBreak();
    }

    private static void printInventoryHeader() {
        System.out.println(MessageConstants.INVENTORY_STATUS_MESSAGE);
        printLineBreak();
    }

    private static void printInventoryProducts(List<Product> inventory) {
        for (Product product : inventory) {
            printProductInfo(product.getName(), product.getPrice(), product.getQuantity(), product.getPromotion());
        }
    }

    private static void printGroupedProducts(List<Product> inventory) {
        Map<String, List<Product>> productGroup = inventory.stream()
                .collect(Collectors.groupingBy(Product::getName));

        productGroup.forEach((name, products) -> {
            if (products.size() > 1) {
                printMultipleProducts(products);
            } else {
                printSingleProductWithOutOfStock(products.get(0));
            }
        });
    }

    private static void printMultipleProducts(List<Product> products) {
        for (Product product : products) {
            printProductInfo(product.getName(), product.getPrice(), product.getQuantity(), product.getPromotion());
        }
    }

    private static void printSingleProductWithOutOfStock(Product product) {
        printProductInfo(product.getName(), product.getPrice(), product.getQuantity(), product.getPromotion());
        printProductInfo(product.getName(), product.getPrice(), 0L, StringConstants.EMPTY);
    }

    private static void printProductInfo(String name, long price, long quantity, String promotion) {
        String convertedProductInfo = InputParser.inventoryParser(name, price, quantity, promotion);
        System.out.println(convertedProductInfo);
    }

    public static void printFinalReceipt(List<Receipt> receipts, Long promotionDiscount, Long membershipDiscount) {
        System.out.println(StringConstants.RECEIPT_HEADER);
        Long sumOfTotalPrice = printPurchaseSummary(receipts);
        printPromotionSummary(receipts);
        printFinalSummary(promotionDiscount, membershipDiscount, sumOfTotalPrice);
        printLineBreak();
    }

    private static void printFinalSummary(Long promotionDiscount, Long membershipDiscount, Long sumOfTotalPrice) {
        System.out.println(StringConstants.SUMMARY_SEPARATOR);
        locateTwoSectionReceipt(List.of(StringConstants.TOTAL_PRICE_AMOUNT, InputParser.giveCommaToPrice(
                sumOfTotalPrice.toString().split("")).toString()));
        locateTwoSectionReceipt(List.of(StringConstants.PROMOTION_DISCOUNT, giveMinusToNumber(promotionDiscount)));
        locateTwoSectionReceipt(List.of(StringConstants.MEMBERSHIP_DISCOUNT, giveMinusToNumber(membershipDiscount)));

        long totalPriceToPay = sumOfTotalPrice - promotionDiscount - membershipDiscount;
        locateTwoSectionReceipt(List.of(StringConstants.MONEY_TO_PAY, InputParser.giveCommaToPrice(
                Long.toString(totalPriceToPay).split("")).toString()));
    }

    private static Long printPurchaseSummary(List<Receipt> receipts) {
        printPurchaseHeader();
        Long sumOfTotalPrice = 0L;
        for (Receipt receipt : receipts) {
            String priceWithComma = InputParser.giveCommaToPrice(receipt.getTotalPrice().toString().split(""))
                    .toString();
            locateTreeSectionReceipt(
                    List.of(receipt.getProductName(), receipt.getTotalQuantity().toString(), priceWithComma));
            sumOfTotalPrice += receipt.getTotalPrice();
        }
        return sumOfTotalPrice;
    }

    private static void printPromotionSummary(List<Receipt> receipts) {
        System.out.println(StringConstants.PROMOTION_SECTION_HEADER);
        List<Receipt> discountReceipts = receipts.stream().filter(receipt -> receipt.getDiscountPrice() > 0).toList();
        for (Receipt receipt : discountReceipts) {
            locateTreeSectionReceipt(List.of(receipt.getProductName(), receipt.getDiscountQuantity().toString()));
        }
    }

    private static String giveMinusToNumber(Long number) {
        return "-" + InputParser.giveCommaToPrice(number.toString().split(""));
    }

    private static void printPurchaseHeader() {
        String nameConstant = StringConstants.RECEIPT_PRODUCT_NAME;
        String quantityConstant = StringConstants.RECEIPT_PRODUCT_QUANTITY;
        String priceConstant = StringConstants.RECEIPT_PRODUCT_PRICE;
        locateTreeSectionReceipt(List.of(nameConstant, quantityConstant, priceConstant));
    }

    private static void locateTwoSectionReceipt(List<String> strings) {
        for (String string : strings) {
            System.out.print(string);
            System.out.print(
                    StringConstants.BLANK.repeat(NumberConstants.RECEIPT_TWO_SECTION_WIDTH - string.length()));
        }
        printLineBreak();
    }

    private static void locateTreeSectionReceipt(List<String> strings) {
        for (String string : strings) {
            System.out.print(string);
            System.out.print(
                    StringConstants.BLANK.repeat(NumberConstants.RECEIPT_THREE_SECTION_WIDTH - string.length()));
        }
        printLineBreak();
    }

    private static void printLineBreak() {
        System.out.println();
    }
}
