package store.view;

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
            return giveCommaToPrice(splitPrice).toString();
        }
        return price+ StringConstants.WON;
    }

    private static StringBuilder giveCommaToPrice(String[] splitPrice) {
        StringBuilder priceWithCurrency = new StringBuilder();
        for (int i = 0; i < splitPrice.length; i++) {
            if (i % 3 == 1) {
                priceWithCurrency.append(StringConstants.COMMA);
            }
            priceWithCurrency.append(splitPrice[i]);
        }
        return priceWithCurrency.append(StringConstants.WON);
    }

    private static StringBuilder appendAll(StringBuilder stringbuilder, String... strings) {
        for (String string : strings) {
            stringbuilder.append(string);
        }
        return stringbuilder;
    }
}
