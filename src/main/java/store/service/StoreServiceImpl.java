package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import store.common.constants.ErrorConstants;
import store.common.constants.NumberConstants;
import store.common.constants.StringConstants;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;
import store.view.InputView;

public class StoreServiceImpl implements StoreService {
    @Override
    public Receipt buy(String name, Long quantity, List<Product> inventory, List<Promotion> promotions) {
        List<Product> products = findProductByName(name, inventory);

        Product[] separatedProducts = separatePromotionProduct(products);
        Product nonPromotionProduct = separatedProducts[0];
        Product promotionProduct = separatedProducts[1];

        if (promotionProduct != null) {
            Promotion promotion = findApplicablePromotion(promotionProduct, promotions);
            boolean isPromotionPeriod = promotion.checkPromotionPeriod(DateTimes.now().toLocalDate());

            if (isPromotionPeriod) {
                return handlePromotionPurchase(promotionProduct, nonPromotionProduct, promotion, quantity);
            }
        }

        return handleNonPromotionPurchase(nonPromotionProduct, quantity);
    }

    @Override
    public Long [] calculateTotalReceipts(List<Receipt> receipts) {
        Long totalPrice = 0L;
        Long discountPrice = 0L;

        for (Receipt receipt : receipts) {
            totalPrice += receipt.getTotalPrice();
            discountPrice += receipt.getDiscountPrice();
        }
        return new Long[] {totalPrice, discountPrice};
    }

    @Override
    public Long calculateMembershipDiscount(Long price, String answer) {
        if (StringConstants.YES.equals(answer)) {
            double membershipDiscountPrice = price * NumberConstants.MEMBERSHIP_DISCOUNT_RATIO;
            if (membershipDiscountPrice > NumberConstants.MAX_MEMBERSHIP_DISCOUNT) {
                return NumberConstants.MAX_MEMBERSHIP_DISCOUNT;
            }
            return (long) membershipDiscountPrice;
        }
        return NumberConstants.NO_MEMBERSHIP_DISCOUNT;
    }

    // 제품 이름으로 Product 리스트를 검색
    private List<Product> findProductByName(String name, List<Product> inventory) {
        List<Product> products = inventory.stream()
                .filter(component -> component.getName().equals(name))
                .toList();

        if (products.isEmpty()) {
            throw new IllegalArgumentException(ErrorConstants.NOT_EXIST_PRODUCT_ERROR_MESSAGE);
        }
        return products;
    }

    // 일반 제품과 프로모션 제품을 구분하여 배열로 반환
    private Product[] separatePromotionProduct(List<Product> products) {
        Supplier<Stream<Product>> productStreamSupplier = products::stream;

        Product nonPromotionProduct = productStreamSupplier.get()
                .filter(product -> StringConstants.NULL.equals(product.getPromotion()))
                .findFirst()
                .orElse(null);

        Product promotionProduct = productStreamSupplier.get()
                .filter(product -> !StringConstants.NULL.equals(product.getPromotion()))
                .findFirst()
                .orElse(null);

        return new Product[]{nonPromotionProduct, promotionProduct};
    }

    // 프로모션이 적용된 제품의 Promotion 반환
    private Promotion findApplicablePromotion(Product promotionProduct, List<Promotion> promotions) {
        return promotions.stream()
                .filter(promo -> promo.getName().equals(promotionProduct.getPromotion()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorConstants.NOT_EXIST_PROMOTION_ERROR_MESSAGE));
    }

    // 프로모션 제품 구매 처리
    private Receipt handlePromotionPurchase(Product promotionProduct, Product nonPromotionProduct,
                                            Promotion promotion, Long quantity) {
        Long promotionProductQuantity = promotionProduct.getQuantity();
        Long freeQuantity = calculateFreeQuantity(quantity, promotion);

        if (promotionProductQuantity >= quantity + freeQuantity) {
            return applyFullPromotion(promotionProduct, quantity, freeQuantity, promotion);
        }
        if (promotionProductQuantity < quantity + freeQuantity) {
            return handleInsufficientPromotionStock(promotionProduct, nonPromotionProduct,
                    promotionProductQuantity, promotion, quantity, freeQuantity);
        }
        throw new IllegalArgumentException(ErrorConstants.NOT_EXIST_CASE_ERROR_MESSAGE);
    }

    // 프로모션이 적용되는 상품에 대해 무료 수량 계산
    private Long calculateFreeQuantity(Long quantity, Promotion promotion) {
        return (quantity / promotion.getBuy()) * promotion.getGet();
    }

    // 프로모션이 전체 수량이 구매 수량과 무료 수량의 합보다 클 때
    private Receipt applyFullPromotion(Product promotionProduct, Long quantity, Long freeQuantity,
                                       Promotion promotion) {
        if (quantity % promotion.getBuy() == 0) {
            String answer = InputView.tellFreeProductProvide(promotionProduct.getName(), freeQuantity);
            Receipt productReceipt = buyProductWithPromotionOrNot(promotionProduct, quantity, freeQuantity, answer);
            if (productReceipt != null) {
                return productReceipt;
            }
        }
        promotionProduct.buy(quantity + freeQuantity);
        return new Receipt(promotionProduct.getName(), quantity + freeQuantity, freeQuantity,
                promotionProduct.getPrice());
    }

    // 프로모션 할인 구매 적용에 따른 구매 처리
    private static Receipt buyProductWithPromotionOrNot(Product promotionProduct, Long quantity, Long freeQuantity, String answer) {
        if (StringConstants.YES.equals(answer)) {
            promotionProduct.buy(quantity + freeQuantity);
            return new Receipt(promotionProduct.getName(), quantity + freeQuantity, freeQuantity,
                    promotionProduct.getPrice());
        }
        if (StringConstants.NO.equals(answer)) {
            promotionProduct.buy(quantity);
            return new Receipt(promotionProduct.getName(), quantity, 0L, promotionProduct.getPrice());
        }
        return null;
    }

    // 프로모션이 전체 수량이 구매 수량과 무료 수량의 합보다 작을 때
    private Receipt handleInsufficientPromotionStock(Product promotionProduct, Product nonPromotionProduct, Long promotionProductQuantity,
                                                     Promotion promotion, Long quantity, Long freeQuantity) {
        Long promotionNotAppliedQuantity = promotionProductQuantity % (promotion.getBuy() + promotion.getGet());
        String answer = InputView.tellPromotionNotApplicable(promotionProduct.getName(), promotionNotAppliedQuantity);

        return buyProductByAnswer(promotionProduct, nonPromotionProduct, promotionProductQuantity, quantity,
                freeQuantity,
                answer, promotionNotAppliedQuantity);
    }

    // 프로모션 재고 부족 시 부족분 정가 구매 혹은 미구매 처리
    private static Receipt buyProductByAnswer(Product promotionProduct, Product nonPromotionProduct, Long promotionProductQuantity,
                                              Long quantity, Long freeQuantity, String answer, Long promotionNotAppliedQuantity) {
        Receipt allProductReceipt = answerYes(promotionProduct, nonPromotionProduct, promotionProductQuantity,
                quantity, freeQuantity, answer, promotionNotAppliedQuantity);
        if (allProductReceipt != null) return allProductReceipt;

        Receipt promotionProductReceipt = answerNo(promotionProduct, quantity, freeQuantity, answer,
                promotionNotAppliedQuantity);
        if (promotionProductReceipt != null) return promotionProductReceipt;

        throw new IllegalArgumentException(ErrorConstants.INPUT_FORMAT_ERROR_MESSAGE);
    }

    // 프로모션 재고 부족 시 부족분 미구매
    private static Receipt answerNo(Product promotionProduct, Long quantity, Long freeQuantity,
                                    String answer, Long promotionNotAppliedQuantity) {
        if (StringConstants.NO.equals(answer)) {
            long quantityToBuy = quantity - promotionNotAppliedQuantity;
            promotionProduct.buy(quantityToBuy);
            return new Receipt(promotionProduct.getName(), quantityToBuy, freeQuantity - promotionNotAppliedQuantity,
                    promotionProduct.getPrice());
        }
        return null;
    }

    // 프로모션 재고 부족 시 부족분 정가 구매
    private static Receipt answerYes(Product promotionProduct, Product nonPromotionProduct, Long promotionProductQuantity,
                                     Long quantity, Long freeQuantity, String answer, Long promotionNotAppliedQuantity) {
        if (StringConstants.YES.equals(answer)) {
            promotionProduct.buy(promotionProductQuantity);
            nonPromotionProduct.buy(quantity - promotionProductQuantity);
            return new Receipt(promotionProduct.getName(), quantity, freeQuantity - promotionNotAppliedQuantity,
                    promotionProduct.getPrice());
        }
        return null;
    }

    // 프로모션이 적용되지 않는 일반 상품 구매 처리
    private Receipt handleNonPromotionPurchase(Product nonPromotionProduct, Long quantity) {
        nonPromotionProduct.buy(quantity);
        return new Receipt(nonPromotionProduct.getName(), quantity, 0L, nonPromotionProduct.getPrice());
    }
}
