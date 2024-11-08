package store.service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import store.common.constants.StringConstants;
import store.domain.Product;
import store.domain.dto.ProductDto;

public class ProductService {

    public List<ProductDto> extractProductByFile(Scanner productsFile) {
        List<Product> inventory = convertFileToProduct(productsFile);

        return convertProducts(inventory);
    }

    private List<Product> convertFileToProduct(Scanner productsFile) {
        List<Product> inventory = productsFile.tokens()
                // 첫 번째 header row 필터
                .filter(line -> !Objects.equals(line, StringConstants.PRODUCTS_FILE_HEADER))
                .map(line -> new Product(line.split(StringConstants.COMMA)))
                .collect(Collectors.toList());
        productsFile.close();

        return inventory;
    }

    private List<ProductDto> convertProducts(List<Product> products) {
        return products.stream().map(product -> new ProductDto(
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.getPromotion()
                )
        ).collect(Collectors.toList());
    }
}
