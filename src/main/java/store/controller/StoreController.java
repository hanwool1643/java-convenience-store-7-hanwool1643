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
import store.service.FileServiceImpl;
import store.service.StoreServiceImpl;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    public StoreController() {
    }

    public void run() {
        FileServiceImpl fileServiceImpl = new FileServiceImpl();
        StoreServiceImpl storeServiceImpl = new StoreServiceImpl();

        List<Product> inventory = extractProducts(fileServiceImpl); // 제품 추출
        List<Promotion> promotions = extractPromotions(fileServiceImpl); //프로모션 추출

        while (true) {
            String answerToContinue = processPurchase(inventory, storeServiceImpl, promotions);
            if (answerToContinue.equals(StringConstants.NO)) break;
        }
    }

    private static List<Promotion> extractPromotions(FileServiceImpl fileServiceImpl) {
        Scanner promotionFile = FileReader.readFile(AddressConstants.promotionFilePath);
        return fileServiceImpl.extractPromotionByFile(promotionFile);
    }

    private static List<Product> extractProducts(FileServiceImpl fileServiceImpl) {
        Scanner productsFile = FileReader.readFile(AddressConstants.productFilePath);
        return fileServiceImpl.extractProductByFile(productsFile);
    }

    private static String processPurchase(List<Product> inventory, StoreServiceImpl storeServiceImpl, List<Promotion> promotions) {
        OutputView.printWelcome(); // 환영 인사
        OutputView.printInventoryDetail(inventory); // 재고 확인

        List<Receipt> receipts = buyProducts(storeServiceImpl, inventory, promotions); // 구매
        Long[] totalReceipts = storeServiceImpl.calculateTotalReceipts(receipts); // totalReceipts[0]: 총구매 금액, totalReceipts[1]: 총할인 금액
        Long priceAfterPromotionDiscount = totalReceipts[0] - totalReceipts[1];
        Long membershipDiscount = calculateMembershipDiscount(storeServiceImpl, priceAfterPromotionDiscount); // 멤버십 할인 적용

        OutputView.printFinalReceipt(receipts, totalReceipts[1], membershipDiscount); // 영수증 출력

        return InputView.buyAnotherProduct(); // 물건 반복 구매 문의
    }

    private static Long calculateMembershipDiscount(StoreServiceImpl storeServiceImpl, Long priceAfterPromotionDiscount) {
        String answerToApplyMembership = InputView.askMembershipDiscount();
        return storeServiceImpl.applyMembershipOrNot(priceAfterPromotionDiscount, answerToApplyMembership);
    }

    private static List<Receipt> buyProducts(StoreServiceImpl storeServiceImpl, List<Product> inventory,
                                             List<Promotion> promotions) {
        List<Receipt> receipts = new ArrayList<>();
        while (receipts.isEmpty()) {
            try {
                Map<String, Long> productsToBuy = InputView.askPurchaseProduct();
                productsToBuy.forEach((name, quantity) -> {
                    Receipt receipt = storeServiceImpl.buy(name, quantity, inventory, promotions);
                    receipts.add(receipt);
                });
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return receipts;
    }
}
