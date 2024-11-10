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
import store.service.ProductService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    public StoreController() {
    }

    public void open() {
        FileService fileService = new FileService();
        ProductService productService = new ProductService();
        // 제품 추출
        Scanner productsFile = FileReader.readFile(AddressConstants.productFilePath);
        List<Product> inventory = fileService.extractProductByFile(productsFile);

        //프로모션 추출
        Scanner promotionFile = FileReader.readFile(AddressConstants.promotionFilePath);
        List<Promotion> promotions = fileService.extractPromotionByFile(promotionFile);

        String answerToContinue = proceedToBuy(inventory, productService, promotions);

        while (answerToContinue.equals(StringConstants.YES)) {
            answerToContinue = proceedToBuy(inventory, productService, promotions);
        }
    }

    private static String proceedToBuy(List<Product> inventory, ProductService productService,
                                    List<Promotion> promotions) {
        // 환영 인사
        OutputView.printWelcome();
        // 재고 확인
        OutputView.printInventoryDetail(inventory);

        // 구매
        List<Receipt> receipts = getReceipts(productService, inventory, promotions);

        // 총 영수증 계산
        Long[] totalReceipts = productService.calculateTotalReceipts(receipts);
        Long totalPrice = totalReceipts[0];
        Long promotionDiscount = totalReceipts[1];
        Long priceAfterPromotionDiscount = totalPrice - promotionDiscount;

        // 멤버십 할인 적용
        String answerToApplyMembership = InputView.askMembershipDiscount();
        Long membershipDiscount = productService.applyMembershipOrNot(priceAfterPromotionDiscount, answerToApplyMembership);

        // 영수증 출력
        OutputView.printFinalReceipt(receipts, promotionDiscount, membershipDiscount);

        // 물건 반복 구매 문의
        return InputView.buyAnotherProduct();
    }

    private static List<Receipt> getReceipts(ProductService productService, List<Product> inventory,
                                             List<Promotion> promotions) {
        List<Receipt> receipts = new ArrayList<>();
        while (receipts.isEmpty()) {
            try {
                Map<String, Long> productsToBuy = InputView.askPurchaseProduct();
                productsToBuy.forEach((name, quantity) -> {
                    Receipt receipt = productService.buy(name, quantity, inventory, promotions);
                    receipts.add(receipt);
                });
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return receipts;
    }
}
