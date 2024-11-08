package store.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import store.common.constants.StringConstants;
import store.domain.Product;

public class ProductService {

    public List<Product> extractProductByFile(Scanner productsFile) {
        List<Product> inventory = productsFile.tokens()
                // 첫 번째 header row 필터
                .filter(line -> !Objects.equals(line, StringConstants.PRODUCTS_FILE_HEADER))
                .map(line -> new Product(line.split(StringConstants.COMMA)))
                .collect(Collectors.toList());
        productsFile.close();

        return inventory;
    }

    public void buy(Map<String, Long> productWithQuantity, List<Product> products) {

    }
}
