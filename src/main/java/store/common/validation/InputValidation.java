package store.common.validation;

import java.util.Arrays;
import store.common.constants.ErrorConstants;
import store.common.constants.StringConstants;

public class InputValidation {
    private static final String purchaseFormat = "\\[[^\\[\\]-]+\\-\\d+\\]";

    public static void validatePurchaseInput(String input) {
        String[] splitStrings = input.split(StringConstants.COMMA);
        boolean isFormatAllMatch = Arrays.stream(splitStrings).allMatch(str -> str.trim().matches(purchaseFormat));
        if (!isFormatAllMatch) {
            throw new IllegalArgumentException(ErrorConstants.PRODUCT_PURCHASE_FORMAT_ERROR_MESSAGE);
        }
    }
}
