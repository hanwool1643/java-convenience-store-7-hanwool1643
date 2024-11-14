package store;

import store.controller.StoreController;
import store.service.FileService;
import store.service.FileServiceImpl;
import store.service.StoreService;
import store.service.StoreServiceImpl;

public class Application {
    public static void main(String[] args) {
        FileService fileService = new FileServiceImpl();
        StoreService productService = new StoreServiceImpl();

        StoreController storeController = new StoreController(fileService, productService);
        storeController.run();
    }
}
