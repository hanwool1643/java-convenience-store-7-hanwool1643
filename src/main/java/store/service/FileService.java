package store.service;

import java.util.List;
import java.util.Scanner;
import store.domain.Product;
import store.domain.Promotion;

public interface FileService {
    List<Product> extractProduct(Scanner productsFile);

    List<Promotion> extractPromotion(Scanner promotionsFile);
}
