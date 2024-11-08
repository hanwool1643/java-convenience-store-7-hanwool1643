package store.domain.dto;

import java.time.LocalDate;

public record PromotionDto(String name, Long buy, Long get, LocalDate startDate, LocalDate endDate) {
}
