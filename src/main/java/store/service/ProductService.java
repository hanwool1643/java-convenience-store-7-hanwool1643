package store.service;

import store.domain.Product;
import store.domain.dto.ProductDto;

public class ProductService {

    public ProductDto createProduct(String name, Long price, Long quantity, String promotion) {
        Product product = new Product(name, price, quantity, promotion);
        return new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getPromotion()
        );
    }
}
