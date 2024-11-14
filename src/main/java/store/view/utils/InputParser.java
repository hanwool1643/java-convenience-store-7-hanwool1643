package store.view.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import store.common.constants.StringConstants;

public class InputParser {
    public static String inventoryParser(String name, Long price, Long quantity, String promotion) {
        StringBuilder stringBuilderWithDash = new StringBuilder(giveBlankToEnd(StringConstants.DASH));

        return appendAll(
                stringBuilderWithDash,
                giveBlankToEnd(name),
                giveBlankToEnd(parsePrice(price)),
                giveBlankToEnd(parseQuantity(quantity)),
                parsePromotion(promotion)
        ).toString();
    }

    public static Map<String, Long> purchaseInputParser(String[] inputArray) {
        Map<String, Long> purchaseDetail = new HashMap<>();
        Arrays.stream(inputArray).forEach(input -> {
            String bracketsRemoved = input.replace("[", "").replace("]", "");
            String[] inputSplit = bracketsRemoved.split(StringConstants.DASH);
            purchaseDetail.put(inputSplit[0], Long.parseLong(inputSplit[1]));
        });
        return purchaseDetail;
    }

    public static StringBuilder giveCommaToPrice(String[] splitPrice) {
        StringBuilder priceWithComma = new StringBuilder();
        for (int i = splitPrice.length - 1 ; i >= 0; i--) {
            priceWithComma.insert(0, splitPrice[i]);
            if ((splitPrice.length - i) % 3 == 0 && i != 0) {
                priceWithComma.insert(0, StringConstants.COMMA);
            }
        }
        return priceWithComma;
    }

    private static String giveBlankToEnd(String name) {
        return name + StringConstants.BLANK;
    }

    private static String parsePromotion(String promotion) {
        String promotionNullConverted = promotion;
        if (Objects.equals(promotion, "null")) {
            promotionNullConverted = "";
        }
        return promotionNullConverted;
    }

    private static String parseQuantity(Long quantity) {
        String quantityWithPieces = quantity.toString() + StringConstants.PIECES;
        if (quantity == 0) {
            quantityWithPieces = StringConstants.OUT_OF_STOCK;
        }
        return quantityWithPieces;
    }

    private static String parsePrice(Long price) {
        if (price >= 1000) {
            String[] splitPrice = price.toString().split("");
            return giveCommaToPrice(splitPrice).append(StringConstants.WON).toString();
        }
        return price + StringConstants.WON;
    }

    private static StringBuilder appendAll(StringBuilder stringbuilder, String... strings) {
        for (String string : strings) {
            stringbuilder.append(string);
        }
        return stringbuilder;
    }
}
