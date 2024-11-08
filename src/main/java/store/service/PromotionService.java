package store.service;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import store.common.constants.StringConstants;
import store.domain.Promotion;
import store.domain.dto.PromotionDto;

public class PromotionService {

    public List<PromotionDto> extractPromotionByFile(Scanner promotionsFile) {
        List<Promotion> promotions = convertFileToPromotion(promotionsFile);

        return convertPromotions(promotions);
    }

    private List<Promotion> convertFileToPromotion(Scanner promotionsFile) {
        List<Promotion> promotions = promotionsFile.tokens()
                // 첫 번째 header row 필터
                .filter(line -> !Objects.equals(line, StringConstants.PROMOTIONS_FILE_HEADER))
                .map(line -> new Promotion(line.split(StringConstants.COMMA)))
                .collect(Collectors.toList());
        promotionsFile.close();

        return promotions;
    }

    private List<PromotionDto> convertPromotions(List<Promotion> promotions) {
        return promotions.stream().map(promotion -> new PromotionDto(
                        promotion.getName(),
                        promotion.getBuy(),
                        promotion.getGet(),
                        promotion.getStartDate(),
                        promotion.getEndDate()
                )
        ).collect(Collectors.toList());
    }
}
