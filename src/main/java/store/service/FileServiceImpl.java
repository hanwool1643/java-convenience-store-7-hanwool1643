package store.service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import store.common.constants.StringConstants;
import store.domain.Product;
import store.domain.Promotion;

public class FileServiceImpl implements FileService {
    @Override
    public List<Product> extractProductByFile(Scanner productsFile) {
        List<Product> inventory = productsFile.tokens()
                // 첫 번째 header row 필터
                .filter(line -> !Objects.equals(line, StringConstants.PRODUCTS_FILE_HEADER))
                .map(line -> new Product(line.split(StringConstants.COMMA)))
                .collect(Collectors.toList());
        productsFile.close();

        return inventory;
    }
    @Override
    public List<Promotion> extractPromotionByFile(Scanner promotionsFile) {
        List<Promotion> promotions = promotionsFile.tokens()
                // 첫 번째 header row 필터
                .filter(line -> !Objects.equals(line, StringConstants.PROMOTIONS_FILE_HEADER))
                .map(line -> new Promotion(line.split(StringConstants.COMMA)))
                .collect(Collectors.toList());
        promotionsFile.close();

        return promotions;
    }
}
