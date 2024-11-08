package store.controller;

import java.util.List;
import java.util.Scanner;
import store.common.FileReader;
import store.common.constants.AddressConstants;
import store.domain.dto.ProductDto;
import store.service.ProductService;
import store.view.OutputView;

public class StoreController {

    public StoreController() {
    }

    public void open() {
        OutputView.printWelcome();
        // 제품
        Scanner productsFile = FileReader.readFile(AddressConstants.productFilePath);
        ProductService productService = new ProductService();
        List<ProductDto> inventory = productService.extractProductByFile(productsFile);
        OutputView.printInventoryDetail(inventory);

        //프로모션
        Scanner promotionFile = FileReader.readFile(AddressConstants.promotionFilePath);
    }
}
