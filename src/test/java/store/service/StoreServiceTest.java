package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.common.FileReader;
import store.common.constants.AddressConstants;
import store.common.constants.StringConstants;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;

class StoreServiceTest {
    FileService fileService;
    StoreService storeService;
    List<Product> products;
    List<Promotion> promotions;


    @BeforeEach
    void setUp() {
        fileService = new FileServiceImpl();
        storeService = new StoreServiceImpl();
        products = fileService.extractProduct(FileReader.readFile(AddressConstants.productFilePath));
        promotions = fileService.extractPromotion(FileReader.readFile(AddressConstants.promotionFilePath));
    }

    @DisplayName("상품 구매 성공 케이스")
    @Test
    void buyExistProduct() {
        //given
        String productName = "사이다";
        Long productQuantity = 1L;

        //when
        Receipt result = storeService.buy(productName, productQuantity, products, promotions);

        // then
        assertThat(result.getProductName()).isEqualTo(productName);
        assertThat(result.getTotalQuantity()).isEqualTo(1L);
    }

    @DisplayName("존재하지 않는 상품 구매 예외 케이스")
    @Test
    void buyNotFoundProduct() {
        //given
        String productName = "사발면";
        Long productQuantity = 1L;

        //when, then
        assertThrows(IllegalArgumentException.class,
                () -> storeService.buy(productName, productQuantity, products, promotions));
    }

    @DisplayName("프로모션 할인 수량에 충족하여 무료 제품 제공 받는 케이스")
    @Test
    void getFreeProduct() {
        //given
        Product promotionProduct = new Product(new String[] {"사이다", "1000", "5", "2+1"});
        Long quantity = 2L;
        Long freeQuantity = 1L;
        String answer = StringConstants.YES;

        //when
        Receipt result = storeService.getProductFreeOrNot(promotionProduct, quantity, freeQuantity, answer);
        long totalQuantity = quantity + freeQuantity;
        Long price = promotionProduct.getPrice();

        //then
        assertThat(result.getProductName()).isEqualTo(promotionProduct.getName());
        assertThat(result.getTotalPrice()).isEqualTo(totalQuantity * price);
        assertThat(result.getDiscountPrice()).isEqualTo(freeQuantity * price);
    }

    @DisplayName("프로모션 할인 수량에 충족하나 무료 제품 제공 받지 않는 케이스")
    @Test
    void getNoFreeProduct() {
        //given
        Product promotionProduct = new Product(new String[] {"사이다", "1000", "5", "2+1"});
        Long quantity = 2L;
        Long freeQuantity = 1L;
        String answer = StringConstants.NO;

        //when
        Receipt result = storeService.getProductFreeOrNot(promotionProduct, quantity, freeQuantity, answer);
        Long price = promotionProduct.getPrice();

        //then
        assertThat(result.getProductName()).isEqualTo(promotionProduct.getName());
        assertThat(result.getTotalPrice()).isEqualTo(quantity * price);
        assertThat(result.getDiscountPrice()).isEqualTo(0L);
    }

    @DisplayName("프로모션 수량이 구매 수량과 무료 수량의 합보다 작을 때 일반 재고 사는 케이스")
    @Test
    void buyCommonProductWhenPromotionStockIsInsufficient() {
        //given
        Product promotionProduct = new Product(new String[] {"사이다", "1000", "2", "2+1"});
        Product nonPromotionProduct = new Product(new String[] {"사이다", "1000", "5", null});
        Promotion promotion = new Promotion(new String[]{"사이다", "2", "1", "2024-11-01", "2024-11-31"});
        Long quantity = 2L;
        Long actualFreeQuantity = storeService.calculateActualFreeQuantity(promotionProduct.getQuantity(), promotion);
        String answer = StringConstants.YES;
        Long promotionNotAppliedQuantity = storeService.calculatePromotionNotAppliedQuantity(quantity, promotionProduct.getQuantity(), promotion);

        //when
        Receipt result = storeService.buyInSufficientPromotionStockOrNot(promotionProduct, nonPromotionProduct, quantity, actualFreeQuantity, answer, promotionNotAppliedQuantity);
        Long price = promotionProduct.getPrice();
        long totalQuantity = quantity + actualFreeQuantity;

        //then
        assertThat(result.getProductName()).isEqualTo(promotionProduct.getName());
        assertThat(result.getTotalPrice()).isEqualTo(totalQuantity * price);
        assertThat(result.getDiscountPrice()).isEqualTo(0L);
        assertThat(promotionProduct.getQuantity()).isEqualTo(0L);
        assertThat(nonPromotionProduct.getQuantity()).isEqualTo(5L);
    }

}