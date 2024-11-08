package store.service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import store.common.constants.StringConstants;
import store.domain.Product;
import store.domain.dto.ProductDto;

public class ProductService {
    private static final int nameIndex = 0;
    private static final int priceIndex = 1;
    private static final int quantityIndex = 2;
    private static final int promotionIndex = 3;

    public List<ProductDto> extractProductByFile(Scanner productsFile) {
        List<ProductDto> inventory = convertFileToProduct(productsFile);
        productsFile.close();

        return inventory;
    }

    private List<ProductDto> convertFileToProduct(Scanner productsFile) {
        return productsFile.tokens()
                .filter(line -> !Objects.equals(line, StringConstants.PRODUCTS_FILE_HEADER))
                .map(line -> createProduct(line.split(StringConstants.COMMA)))
                .collect(Collectors.toList());
    }

    private ProductDto createProduct(String[] productsInfo) {
        String name = productsInfo[nameIndex];
        Long price = Long.parseLong(productsInfo[priceIndex]);
        Long quantity = Long.parseLong(productsInfo[quantityIndex]);
        String promotion = productsInfo[promotionIndex];
        Product product = new Product(name, price, quantity, promotion);

        return new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getPromotion()
        );
    }
}
