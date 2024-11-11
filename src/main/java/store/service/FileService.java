package store.service;

import java.util.List;
import java.util.Scanner;
import store.domain.Product;
import store.domain.Promotion;

public interface FileService {
    List<Product> extractProductByFile(Scanner productsFile);

    List<Promotion> extractPromotionByFile(Scanner promotionsFile);
}
