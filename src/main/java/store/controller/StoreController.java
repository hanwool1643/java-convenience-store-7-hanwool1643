package store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import store.common.FileReader;
import store.common.constants.AddressConstants;
import store.common.constants.StringConstants;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;
import store.service.FileService;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final FileService fileService;
    private final StoreService storeService;

    public StoreController(FileService fileService, StoreService storeService) {
        this.fileService = fileService;
        this.storeService = storeService;
    }

    public void run() {
        List<Product> inventory = extractProducts(fileService); // 제품 추출
        List<Promotion> promotions = extractPromotions(fileService); //프로모션 추출

        while (true) {
            String answerToContinue = processPurchase(inventory, storeService, promotions);
            if (answerToContinue.equals(StringConstants.NO)) break;
        }
    }

    private static List<Promotion> extractPromotions(FileService fileServiceImpl) {
        Scanner promotionFile = FileReader.readFile(AddressConstants.promotionFilePath);
        return fileServiceImpl.extractPromotion(promotionFile);
    }

    private static List<Product> extractProducts(FileService fileServiceImpl) {
        Scanner productsFile = FileReader.readFile(AddressConstants.productFilePath);
        return fileServiceImpl.extractProduct(productsFile);
    }

    private static String processPurchase(List<Product> inventory, StoreService storeService, List<Promotion> promotions) {
        OutputView.printWelcome(); // 환영 인사
        OutputView.printInventoryDetail(inventory); // 재고 확인

        List<Receipt> receipts = buyProducts(storeService, inventory, promotions); // 구매
        Long[] totalReceipts = storeService.calculateTotalReceipts(receipts); // totalReceipts[0]: 총구매 금액, totalReceipts[1]: 총할인 금액
        Long priceAfterPromotionDiscount = totalReceipts[0] - totalReceipts[1];
        Long membershipDiscount = calculateMembershipDiscount(storeService, priceAfterPromotionDiscount); // 멤버십 할인 적용

        OutputView.printFinalReceipt(receipts, totalReceipts[1], membershipDiscount); // 영수증 출력

        return InputView.buyAnotherProduct(); // 물건 반복 구매 문의
    }

    private static Long calculateMembershipDiscount(StoreService storeService, Long priceAfterPromotionDiscount) {
        String answerToApplyMembership = InputView.askMembershipDiscount();
        return storeService.calculateMembershipDiscount(priceAfterPromotionDiscount, answerToApplyMembership);
    }

    private static List<Receipt> buyProducts(StoreService storeService, List<Product> inventory,
                                             List<Promotion> promotions) {
        List<Receipt> receipts = new ArrayList<>();
        while (receipts.isEmpty()) {
            try {
                Map<String, Long> productsToBuy = InputView.askPurchaseProduct();
                productsToBuy.forEach((name, quantity) -> {
                    Receipt receipt = storeService.buy(name, quantity, inventory, promotions);
                    receipts.add(receipt);
                });
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return receipts;
    }
}
