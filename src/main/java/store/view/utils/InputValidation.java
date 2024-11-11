package store.view.utils;

import java.util.Arrays;
import store.common.constants.ErrorConstants;
import store.common.constants.StringConstants;

public class InputValidation {
    public static String[] validatePurchaseInput(String input) {
        String[] inputSplit = input.split(StringConstants.COMMA);
        boolean isFormatAllMatch = Arrays.stream(inputSplit).allMatch(str -> str.trim().matches(StringConstants.PURCHASE_INPUT_FORMAT));
        if (!isFormatAllMatch) {
            throw new IllegalArgumentException(ErrorConstants.PRODUCT_PURCHASE_FORMAT_ERROR_MESSAGE);
        }
        return inputSplit;
    }
}
