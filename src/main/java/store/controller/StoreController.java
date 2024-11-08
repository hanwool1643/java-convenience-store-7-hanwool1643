package store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.common.FileReader;
import store.common.constants.AddressConstant;
import store.common.constants.StringConstants;
import store.domain.dto.ProductDto;
import store.service.ProductService;
import store.view.OutputView;

public class StoreController {

    public StoreController() {
    }

    public void open() {
        OutputView.printWelcome();
        Scanner productsFile = FileReader.readFile(AddressConstant.productFilePath);
        List<ProductDto> inventory = new ArrayList<>();
        ProductService productService = new ProductService();
        int scannerLine = 0;
        while (productsFile.hasNextLine()) {
            String line = productsFile.nextLine();
            if (scannerLine != 0) {
                String[] productsInfo = line.split(StringConstants.COMMA);
                ProductDto product = productService.createProduct(
                        productsInfo[0],
                        Long.parseLong(productsInfo[1]),
                        Long.parseLong(productsInfo[2]),
                        productsInfo[3]
                );
                inventory.add(product);
            }
            scannerLine++;
        }
        productsFile.close();
        OutputView.printInventoryDetail(inventory);
    }
}
