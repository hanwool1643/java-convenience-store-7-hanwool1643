package store.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import store.common.FileReader;
import store.common.constants.AddressConstants;
import store.domain.Product;
import store.domain.Promotion;
import store.service.ProductService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    public StoreController() {
    }

    public void open() {
        OutputView.printWelcome();
        ProductService productService = new ProductService();
        // 제품
        Scanner productsFile = FileReader.readFile(AddressConstants.productFilePath);
        List<Product> inventory = productService.extractProductByFile(productsFile);
        OutputView.printInventoryDetail(inventory);

        Map<String, Long> productWithQuantity = InputView.askPurchaseProduct();
        productService.buy(productWithQuantity, inventory);

        //프로모션
        Scanner promotionFile = FileReader.readFile(AddressConstants.promotionFilePath);
        PromotionService promotionService = new PromotionService();
        List<Promotion> promotions = promotionService.extractPromotionByFile(promotionFile);
    }
}
